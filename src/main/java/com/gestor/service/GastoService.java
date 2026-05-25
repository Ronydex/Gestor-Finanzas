package com.gestor.service;

import com.gestor.dto.GastoDTO;
import com.gestor.dto.GastoResponseDTO;
import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.model.CategoriaKakeibo;
import com.gestor.model.Usuario;
import com.gestor.repository.GastoRepository;
import com.gestor.repository.UsuarioRepository;
import com.gestor.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GastoService {
    
    @Autowired
    private GastoRepository gastoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;
    
    public List<GastoResponseDTO>  filtrarPorPeriodo(String email, String periodo){
        //1.Obtenemos todos los gastos del usuario desde el repositorio
        List<Gasto> listaCompleta = gastoRepo.buscarGastosPorEmailUsuario(email);

        //2.Obtenemos la fecha actual del sistema
        LocalDate hoy = LocalDate.now();

        //3.Iniciamos el procesamiento con Streams
       return listaCompleta.stream()
            .filter(g -> {
                if(g.getFecha() == null) return false;

                switch(periodo.toLowerCase()) {
                  case "dia":
                  //Compara si la fecha del gasto es exactamente hoy
                  return g.getFecha().isEqual(hoy);

                  case "semana":
                  //Calculamos el inicio de la semana(Lunes)
                  LocalDate inicioSemana= hoy.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                  //Verificamos si el gasto ocurrio desde el lunes hasta hoy
                  return !g.getFecha().isBefore(inicioSemana) && !g.getFecha().isAfter(hoy);

                  case "mes":
                  //Compara que el mes y el año coincida con los actuales
                  return g.getFecha().getMonth() == hoy.getMonth() && g.getFecha().getYear() == hoy.getYear();

                  case "anio":
                  //Filtra solo por el año actual
                  return g.getFecha().getYear() == hoy.getYear();

                  default:
                    // Si no se especifica  o es "todos" , devuelve la lista completa
                    return true;


                }
            })
            .map(this::convertirAConvertirResponseDTO)
            .collect(Collectors.toList());
    }
    
    public void guardarGasto(GastoDTO dto, String email) {
        // Buscamos al usuario por su email
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapeo manual: Pasamos los datos del DTO a la Entidad
        Gasto gasto = new Gasto();
        gasto.setDescripcion(dto.getDescripcion());
        gasto.setMonto(dto.getMonto());
        gasto.setTipo(dto.getTipo());
        

        if (dto.getCategoria() != null){
            gasto.setCategoria(CategoriaKakeibo.valueOf(dto.getCategoria()));
        }
        
        // La fecha se asigna automáticamente (Lógica de negocio)
        gasto.setFecha(LocalDate.now());
        
        // Vinculamos el usuario
        gasto.setUsuario(usuario);

        // Guardamos en la base de datos
        gastoRepo.save(gasto);
    }

    // 2. Lógica para calcular el saldo total
    public Double calcularSaldoTotalPorUsuario(String email){
        List<Gasto> gastosUsuario = gastoRepo.buscarGastosPorEmailUsuario(email);
        
        double ingresos = gastosUsuario.stream()
                .filter(g -> g.getTipo() == TipoTransaccion.INGRESO)
                .mapToDouble(g -> g.getMonto() != null ? g.getMonto() : 0.0)
                .sum();

        double egresos = gastosUsuario.stream()
                .filter(g -> g.getTipo() == TipoTransaccion.GASTO || g.getTipo() == TipoTransaccion.EGRESO)
                .mapToDouble(g -> g.getMonto() != null ? g.getMonto() : 0.0)
                .sum();
        
        return ingresos - egresos;
    }

    // 3. Obtener datos para el Dashboard (Gráficas)
    public Map<String, Object> obtenerDatosDashboard(String email,String periodo){
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("El Usuario con el correo " + email + "no está registrado en el sistema."));

        List<GastoResponseDTO> listaFiltrada = filtrarPorPeriodo(email,periodo);

        Double saldo = calcularSaldoTotalPorUsuario(email);

        // Preparamos las listas para Chart.js
        List<String> descripciones = listaFiltrada.stream()
                .map(GastoResponseDTO::getDescripcion)
                .collect(Collectors.toList());

        List<Double> montos = listaFiltrada.stream()
                .map(GastoResponseDTO::getMonto)
                .collect(Collectors.toList());

        List<String> tipos = listaFiltrada.stream()
                .map(g -> g.getTipo().name())
                .collect(Collectors.toList());

        Map<String, Object> datos = new HashMap<>();
        datos.put("usuario", usuario);
        datos.put("saldo", saldo);
        datos.put("gastos", listaFiltrada); // Necesario para la tabla
        datos.put("descripciones", descripciones); // Para la gráfica
        datos.put("montos", montos); // Para la gráfica
        datos.put("tipos", tipos); // Para la gráfica

        return datos;
    }

    public void eliminarGasto(Long id) {
        gastoRepo.deleteById(id);
    }

    public GastoResponseDTO convertirAConvertirResponseDTO(Gasto gasto) {
        return new GastoResponseDTO(
            gasto.getId(),
            gasto.getDescripcion(),
            gasto.getMonto(),
            gasto.getTipo(),
            gasto.getCategoria() != null ? gasto.getCategoria().name() : "SIN CATEGORÍA",
            gasto.getFecha() !=null ? gasto.getFecha().toString() : ""
        );
    }
}
