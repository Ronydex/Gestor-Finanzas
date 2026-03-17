package com.gestor.controller;

import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.model.Usuario;
import com.gestor.repository.GastoRepository;
import com.gestor.repository.UsuarioRepository;
import com.gestor.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class GastoController {

	
	@Autowired
	private GastoRepository gastoRepo;
	
	@Autowired
	private GastoService gastoService;

	@Autowired
	private UsuarioRepository usuarioRepo;


	@PostMapping("/gastos/nuevo-form")
	public String crearGastoForm(@Valid @ModelAttribute("gasto") Gasto gasto,
								 BindingResult result,
								 Principal principal,
								 Model model) {
		if (result.hasErrors()) {
			String email = principal.getName();

			model.addAttribute("usuario", usuarioRepo.findByEmail(email).orElseThrow());
			model.addAttribute("gastos", gastoRepo.findByUsuarioEmail(email));
			model.addAttribute("saldo", gastoService.calcularSaldoTotalPorUsuario(email));

			List<Gasto> lista = gastoRepo.findByUsuarioEmail(email);
			model.addAttribute("descripciones", lista.stream().map(Gasto::getDescripcion).toList());
			model.addAttribute("montos" , lista.stream().map(Gasto::getMonto).toList());
			model.addAttribute("tipos", lista.stream().map(Gasto::getTipo).toList());

			return "listado";
		}


		//Al estar todo bien,se guarda:
		Usuario usuarioActual = usuarioRepo.findByEmail(principal.getName()).orElseThrow();
		gasto.setUsuario(usuarioActual);
		gasto.setFecha(LocalDate.now());

		gastoRepo.save(gasto);
		return "redirect:/ver-todo";
	}

	@GetMapping("/saldo")
	public String verSaldo(Principal principal){

		String email= principal.getName();
		Double saldo = gastoService.calcularSaldoTotalPorUsuario(email);
		String mensaje = (saldo >=0) ?  "Vas por buen camino" : "Cuidado estás en números rojos ";

		return "<h1>Tu saldo actual es: $" + saldo + "</h1><p>" + mensaje + "</p>";
	}
	
	@GetMapping("/ver-todo")
	public String mostrarTabla(Model model, Principal principal) {

		String emailUsuario = principal.getName();

		Usuario usuario = usuarioRepo.findByEmail(emailUsuario)
				.orElseThrow(() ->new RuntimeException("No se encontro al usuario"));

		List<Gasto> lista = gastoRepo.findByUsuarioEmail(emailUsuario);

		Double saldo = gastoService.calcularSaldoTotalPorUsuario(emailUsuario);

		List<String> descripciones = lista.stream()
				.map(Gasto::getDescripcion)
				.collect(Collectors.toList());

		List<Double> montos = lista.stream()
				.map(Gasto::getMonto)
				.collect(Collectors.toList());

		List<String> tipos = lista.stream()
				.map(g ->g.getTipo().name())
				.collect(Collectors.toList());

		//Se envian los datos a la página HTML
		model.addAttribute("usuario", usuario);
		model.addAttribute("gastos", lista);
		model.addAttribute("saldo", saldo);
		model.addAttribute("descripciones", descripciones);
		model.addAttribute("montos", montos);
		model.addAttribute("tipos", tipos);
		model.addAttribute("gasto", new Gasto());
		
		return "listado";//Busca el archivo templates/listado.html
		}

	@PostMapping("/ver-todo")
	public String mostrarDashboard(Model model){

		List<Gasto> lista = gastoRepo.findAll();

		List<String> descripciones = listarGastos().stream().map(Gasto::getDescripcion).collect(Collectors.toList());
		List<Double> montos = listarGastos().stream().map(Gasto::getMonto).collect(Collectors.toList());

		model.addAttribute("descripciones", descripciones);
		model.addAttribute("montos", montos);
		model.addAttribute("gastos", lista);
		return "dashboard";
	}

	//Ruta para ver todos los gastos
	@GetMapping("/gastos")
	public List<Gasto> listarGastos() {
		return gastoRepo.findAll();
		}
	
	//Ruta rápida para agregar un gasto desde la URL
	
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
	public String eliminar(@PathVariable Long id) {
		gastoRepo.deleteById(id);
		return "redirect:/ver-todo";
	}

	}
