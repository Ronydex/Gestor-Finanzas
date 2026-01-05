package com.gestor.controller;

import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.repository.GastoRepository;
import com.gestor.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;


@Controller
public class GastoController {
	
	@PostMapping("/gastos/nuevo-form")
	public String crearGastoForm(
			@RequestParam String descripcion,
			@RequestParam Double monto,
			@RequestParam String tipo){
				
		TipoTransaccion tipoEnum = TipoTransaccion.valueOf(tipo.toUpperCase());
		Gasto nuevoGasto = new Gasto(descripcion, monto, LocalDate.now(), tipoEnum);
		gastoRepo.save(nuevoGasto);
		
		return "redirect:/ver-todo"; //Esto hace que la página se refresque sola
				
	}
	
	@Autowired
	private GastoRepository gastoRepo;
	
	@Autowired
	private GastoService gastoService;
	
	@GetMapping("/saldo")
	public String verSaldo(){
		Double saldo = gastoService.calcularSaldoTotal();
		String mensaje = (saldo >=0) ?  "Vas por buen camino" : "Cuidado estás en números rojos ";
		
		return "<h1>Tu saldo actual es: $" + saldo + "</h1><p>" + mensaje + "</p>";
	}
	
	@GetMapping("/ver-todo")
	public String mostrarTabla(org.springframework.ui.Model model) {
		List<Gasto> lista = gastoRepo.findAll();
		Double saldo = gastoService.calcularSaldoTotal();
		
		//Se envian los datos a la página HTML
		model.addAttribute("gastos", lista);
		model.addAttribute("saldo", saldo);
		
		return "listado";//Busca el archivo templates/listado.html
		}
		
	//Ruta para ver todos los gastos
	@GetMapping("/gastos")
	public List<Gasto> listarGastos() {
		return gastoRepo.findAll();
		}
	
	//Ruta rápida para agregar un gasto desde la URL (solo para probar por el momento)
	
	@GetMapping("/gastos/nuevo")
	@ResponseBody
	public String crearGasto(
			@RequestParam String descripcion,
			@RequestParam Double monto,
			@RequestParam String tipo){
		
		TipoTransaccion tipoEnum = TipoTransaccion.valueOf(tipo.toUpperCase());
		Gasto nuevoGasto = new Gasto(descripcion, monto, LocalDate.now(), tipoEnum);
		gastoRepo.save(nuevoGasto);
		
		return "Gasto guardado: " + descripcion + " por $" + monto;
		}
		
	@GetMapping("/gastos/eliminar/{id}")
	public String eliminarGasto(@PathVariable Long id){
		gastoRepo.deleteById(id);
		return "redirect:/ver-todo"; //Regresa a la tabla para ver el cambio
		}
	}
