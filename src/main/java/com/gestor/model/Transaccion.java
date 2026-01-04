package com.gestor.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transacciones")
public class Transaccion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTransaccion;
	
	private BigDecimal monto; //Es necesario debido a que se manejara dinero
	private LocalDate fecha;
	private String descripcion;
	
	@Enumerated(EnumType.STRING) //Guarda el texto 'ingreso' o 'egreso'
	private TipoTransaccion tipo;
	
	@ManyToOne // Muchos movimientos pertenecen a UN solo usuario
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
	
	public Long getIdTransaccion(){ return idTransaccion; }
	public void setIdTransaccion (Long idTransaccion){ this.idTransaccion = idTransaccion; }
	
	public BigDecimal getMonto(){ return monto; }
	public void setMonto(BigDecimal monto) { this.monto = monto; }
	
	public LocalDate getFecha(){ return fecha; }
	public void setFecha(LocalDate fecha){ this.fecha = fecha; }
	
	public String getDescripcion(){ return descripcion; }
	public void setDescripcion(String descripcion){ this.descripcion = descripcion; }
	
	public TipoTransaccion getTipo(){ return tipo; }
	public void setTipo(TipoTransaccion tipo){ this.tipo = tipo; }
	
	public Usuario getUsuario(){ return usuario; }
	public void setUsuario(Usuario usuario){ this.usuario = usuario; }
	
	public Categoria getCategoria(){ return categoria; }
    public void setCategoria(Categoria categoria){ this.categoria = categoria; }
    
	}
