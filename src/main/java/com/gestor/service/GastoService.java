package com.gestor.service;

import com.gestor.dto.GastoDTO;
import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.model.Usuario;
import com.gestor.repository.GastoRepository;
import com.gestor.repository.UsuarioRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GastoService {
    
    @Autowired
    private GastoRepository gastoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;
    
    
    public void guardarGasto(GastoDTO dto, String email) {
        // Buscamos al usuario por su email
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapeo manual: Pasamos los datos del DTO a la Entidad
        Gasto gasto = new Gasto();
        gasto.setDescripcion(dto.getDescripcion());
        gasto.setMonto(dto.getMonto());
        gasto.setTipo(dto.getTipo());
        
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
    public Map<String, Object> obtenerDatosDashboard(String email){
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró al usuario"));

        List<Gasto> lista = gastoRepo.buscarGastosPorEmailUsuario(email);
        Double saldo = calcularSaldoTotalPorUsuario(email);

        // Preparamos las listas para Chart.js
        List<String> descripciones = lista.stream()
                .map(Gasto::getDescripcion)
                .collect(Collectors.toList());

        List<Double> montos = lista.stream()
                .map(Gasto::getMonto)
                .collect(Collectors.toList());

        List<String> tipos = lista.stream()
                .map(g -> g.getTipo().name())
                .collect(Collectors.toList());

        Map<String, Object> datos = new HashMap<>();
        datos.put("usuario", usuario);
        datos.put("saldo", saldo);
        datos.put("gastos", lista); // Necesario para la tabla
        datos.put("descripciones", descripciones); // Para la gráfica
        datos.put("montos", montos); // Para la gráfica
        datos.put("tipos", tipos); // Para la gráfica

        return datos;
    }

    public void eliminarGasto(Long id) {
        gastoRepo.deleteById(id);
    }
}
