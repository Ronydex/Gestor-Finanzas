package com.gestor.service;

import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.List;

@Service
public class GastoService {
	
	@Autowired
	private GastoRepository gastoRepo;
	
	//Logica para calcular el saldo total
	public Double calcularSaldoTotalPorUsuario(String email){
		List<Gasto> gastosUsuario = gastoRepo.findByUsuarioEmail(email);
		double ingresos = gastosUsuario.stream()
				.filter(g->g.getTipo() == TipoTransaccion.INGRESO)
				.mapToDouble(g -> g.getMonto() != null ? g.getMonto() : 0.0)
				.sum();

		double egresos = gastosUsuario.stream()
				.filter(g->g.getTipo() == TipoTransaccion.GASTO || g.getTipo() == TipoTransaccion.EGRESO)
				.mapToDouble(g -> g.getMonto() != null ? g.getMonto() : 0.0)
				.sum();
			return ingresos-egresos;
		}
	}
