package com.gestor.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "gastos")
public class Gasto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TipoTransaccion tipo;
	
	private String descripcion;
	private Double monto;
	private LocalDate fecha;
	
	public Gasto(String descripcion, Double monto, LocalDate fecha, TipoTransaccion tipo){
		this.descripcion = descripcion;
		this.monto = monto;
		this.fecha = fecha;
		this.tipo = tipo;
		}
		
	public Gasto(){
		}
		
	public Long getId(){ return id; }
	public void setId(Long id){ this.id = id;}
	
	public String getDescripcion(){ return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	
	public Double getMonto() { return monto; }
	public void setMonto(Double monto){ this.monto = monto; }
	
	public LocalDate getFecha() { return fecha; }
	public void setFecha(LocalDate fecha) { this.fecha = fecha; }
	
	public TipoTransaccion getTipo() { return tipo; }
	public void setTipo( TipoTransaccion tipo) { this.tipo = tipo; }
		

	}

