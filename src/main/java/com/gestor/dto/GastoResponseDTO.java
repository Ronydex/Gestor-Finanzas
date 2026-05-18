package com.gestor.dto;

import com.gestor.model.TipoTransaccion;

public class GastoResponseDTO {

    private Long id;
    private String descripcion;
    private Double monto;
    private TipoTransaccion tipo;
    private String categoria;
    private String fecha;

    //Constructor vacío
    public GastoResponseDTO(){

    }

    //Constructor con parámetros
    public GastoResponseDTO(Long id, String descripcion, Double monto, TipoTransaccion tipo, String categoria, String fecha ){
    this.id = id;
    this.descripcion = descripcion;
    this.monto = monto;
    this.tipo = tipo;
    this.categoria = categoria;
    this.fecha = fecha;
    }

    //Getters y Setters
    public Long getId(){ return id; }
    public void setId(Long id) { this.id = id;}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getMonto() { return monto; }
    public void setMonto(Double  monto) { this.monto = monto; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipoTransaccion (TipoTransaccion tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getFecha() { return fecha; }
    public void setFecha( String fecha) { this.fecha = fecha; }


}
