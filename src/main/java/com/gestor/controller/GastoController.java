package com.gestor.controller;

import com.gestor.dto.GastoDTO;
import com.gestor.dto.MetaAhorroRequestDTO;
import com.gestor.model.Gasto;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
            // Si hay errores de validación, recargamos el mapa completo del Dashboard
            // Esto reabastece los atributos 'gastos', 'descripciones', 'montos', etc., para Chart.js
            Map<String, Object> datosDashboard = gastoService.obtenerDatosDashboard(email, "mes");

            model.addAllAttributes(datosDashboard);
            
            // Mantenemos el 'gastoDTO' que tiene los errores dentro del modelo para que Thymeleaf muestre los invalid-feedback
            return "listado";
        }

        // Si la validación pasa con éxito, se ejecuta el flujo de guardado
        gastoService.guardarGasto(gastoDTO, email);
        
        // POST-REDIRECT-GET: Obliga al navegador a limpiar los campos de envío haciendo un GET
        return "redirect:/ver-todo";
    }

    @GetMapping("/ver-todo")
    public String mostrarTabla(Model model, Principal principal) {
        String email = principal.getName();
        
        // 1. Entregamos un DTO limpio para inicializar el formulario de Kakeibo sin errores previos
        if (!model.containsAttribute("gasto")) {
            model.addAttribute("gasto", new GastoDTO());
        }

        if(!model.containsAttribute("metaAhorro")){
            model.addAttribute("metaAhorro", new MetaAhorroRequestDTO());
        }

        //Obtenemos el año y mes actual del sistema
        YearMonth añoMesActual = YearMonth.now();
        //Definimos el patron exacto que espera tu DTO y Base de Datos (MM-yyyy)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("MM-yyyy");
        //Transformamos la fecha actual en el String dinámico 
        String mesAnioActual = añoMesActual.format(formateador);

        Optional<MetaAhorro> metaOpt = metAhorroService.buscarPorMes(email, mesAnioActual);

        if(metaOpt.isPresent()){
            model.addAttribute("metaActual", metaOpt.get().getMontoObjetivo());
        }else {
            model.addAttribute("metaActual", BigDecimal.ZERO);
        }
        
        // 3. Extraemos todos los datos calculados y filtrados para el periodo "mes"
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
