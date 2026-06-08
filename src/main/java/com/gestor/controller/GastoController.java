package com.gestor.controller;

import com.gestor.dto.GastoDTO;
import com.gestor.dto.MetaAhorroRequestDTO;
import com.gestor.model.Gasto;
import com.gestor.model.MetaAhorro;
import com.gestor.service.MetaAhorroService;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.Optional;
import com.gestor.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private MetaAhorroService metAhorroService;

    @PostMapping("/gastos/nuevo-form")
    public String crearGastoForm(@Valid @ModelAttribute("gasto") GastoDTO gastoDTO,
                                 BindingResult result,
                                 Principal principal,
                                 Model model) {

        String email = principal.getName();

        if (result.hasErrors()) {
            System.out.println("=================================================");
            System.out.println("⚠️ ¡EL FORMULARIO REBOTÓ POR ERRORES DE VALIDACIÓN!");
            result.getFieldErrors().forEach(err -> {
                System.out.println("❌ Campo con error: '" + err.getField() + "'");
                System.out.println("   Valor rechazado: '" + err.getRejectedValue() + "'");
                System.out.println("   Motivo: " + err.getDefaultMessage());
            });
            System.out.println("=================================================");
            
            Map<String, Object> datosDashboard = gastoService.obtenerDatosDashboard(email, "mes");
            model.addAllAttributes(datosDashboard);
            
            YearMonth añoMesActual = YearMonth.now();
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("MM-yyyy");
            String mesAnioActual = añoMesActual.format(formateador);

            if(!model.containsAttribute("metaAhorro")){
               MetaAhorroRequestDTO nuevoDTO = new MetaAhorroRequestDTO();
               nuevoDTO.setMesAnio(mesAnioActual);
               model.addAttribute("metaAhorro", nuevoDTO);
            }

            Optional<MetaAhorro> metaOpt = metAhorroService.buscarMetaPorMes(email, mesAnioActual);
            model.addAttribute("metaActual", metaOpt.isPresent() ? metaOpt.get().getMontoObjetivo() : BigDecimal.ZERO);

            return "listado";
        }

        gastoService.guardarGasto(gastoDTO, email);
        return "redirect:/ver-todo";
    }

    // ==========================================
    // ¡MÉTODO POST TOTALMENTE CORREGIDO Y LIMPIO!
    // ==========================================
    @PostMapping("/ver-todo")
    public String guardarMeta(@Valid @ModelAttribute("metaAhorro") MetaAhorroRequestDTO metaDTO, 
                              BindingResult result,
                              Principal principal,
                              Model model) {

        String email = principal.getName();

        if (result.hasErrors()){
            Map<String, Object> datos = gastoService.obtenerDatosDashboard(email, "mes");
            model.addAllAttributes(datos);
            if (!model.containsAttribute("gasto")) model.addAttribute("gasto", new GastoDTO());
            return "listado";
        }

        // Invocamos el método correcto de tu MetaAhorroService pasando el DTO directo
        metAhorroService.guardarOActualizarMeta(metaDTO, email);

        // POST-REDIRECT-GET para limpiar el flujo del navegador
        return "redirect:/ver-todo";
    }

    @GetMapping("/ver-todo")
    public String mostrarTabla(Model model, Principal principal) {
        String email = principal.getName();
       
        YearMonth añoMesActual = YearMonth.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("MM-yyyy");
        String mesAnioActual = añoMesActual.format(formateador);

        if (!model.containsAttribute("gasto")) {
            model.addAttribute("gasto", new GastoDTO());
        }

        if(!model.containsAttribute("metaAhorro")){
            MetaAhorroRequestDTO nuevoDTO = new MetaAhorroRequestDTO();
            nuevoDTO.setMesAnio(mesAnioActual);

            Optional<MetaAhorro> metaExistente = metAhorroService.buscarMetaPorMes(email, mesAnioActual);

            // Si ya existe una meta en base de datos, rellenamos el input del formulario automáticamente
            if(metaExistente.isPresent()){
                 nuevoDTO.setMontoObjetivo(metaExistente.get().getMontoObjetivo()); 
            }

            model.addAttribute("metaAhorro", nuevoDTO);
        }

        Optional<MetaAhorro> metaOpt = metAhorroService.buscarMetaPorMes(email, mesAnioActual);

        if(metaOpt.isPresent()){
            model.addAttribute("metaActual", metaOpt.get().getMontoObjetivo());
        } else {
            model.addAttribute("metaActual", BigDecimal.ZERO);
        }
        
        Map<String, Object> datos = gastoService.obtenerDatosDashboard(email, "mes");
        model.addAllAttributes(datos);
        
        return "listado";
    }

    @GetMapping("/gastos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return "redirect:/ver-todo";
    }
}
