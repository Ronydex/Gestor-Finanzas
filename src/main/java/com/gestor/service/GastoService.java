package com.gestor.service;

import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {
	
	@Autowired
	private GastoRepository gastoRepo;
	
	//Logica para calcular el saldo total
	public Double calcularSaldoTotal(){
		List<Gasto> todos = gastoRepo.findAll();
		Double saldo = 0.0;
		
		for (Gasto g: todos) {
			if (g.getTipo() == TipoTransaccion.INGRESO) {
				saldo += g.getMonto();
				}else {
					saldo -= g.getMonto();
					}
			}
			return saldo;
		}
	}
