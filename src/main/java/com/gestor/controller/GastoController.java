package com.gestor.controller;

import com.gestor.dto.GastoDTO;
import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.model.Usuario;
import com.gestor.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;



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

         // Ejemplo rápido de cómo usarlo en el Controller
         List<Gasto> filtrados = gastoService.filtrarPorPeriodo(email, "mes");
         model.addAttribute("gastos", filtrados);

		if (result.hasErrors()){
            
            //Si hay errores,volvemos a pedir los datos para no romper las graficas
			model.addAllAttributes(gastoService.obtenerDatosDashboard(email,"mes"));
			return "listado";
		}

		//El Service se encarga de buscar al usuario,poner la fecha y guardar 
		gastoService.guardarGasto(gastoDTO, email);
		return "redirect:/ver-todo";
	}

	@GetMapping("/ver-todo")
	public String mostrarTabla(Model model, Principal principal) {
        model.addAttribute("gasto", new GastoDTO());
        String email = principal.getName();
        Map<String, Object> datos = gastoService.obtenerDatosDashboard(email, "mes");
        model.addAllAttributes(datos);
        return "listado";
		}

	@GetMapping("/gastos/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		return "redirect:/ver-todo";
	}

}
