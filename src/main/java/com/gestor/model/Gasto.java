package com.gestor.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "gastos")
public class Gasto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TipoTransaccion tipo;

	@NotBlank(message= "La descripción no puede estar vacía")
	@Size(min= 3, max= 50, message="La descripción debe tener entre 3 y 50 carácteres")
	private String descripcion;

	@NotNull(message= "El monto es obligatorio")
	@Positive(message= "El monto debe ser un número positivo")
	private Double monto;

	@PastOrPresent(message="La fecha no debe ser futura")
	private LocalDate fecha;

    

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

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

	public Usuario getUsuario() { return usuario; }
	public void setUsuario(Usuario usuario){ this.usuario = usuario;}

	}

