package com.gestor.dto;

import com.gestor.model.TipoTransaccion;
import com.gestor.model.CategoriaKakeibo;
import javax.validation.constraints.*;

public class GastoDTO{

    @NotBlank(message="La descripcion no debe estar vacia")
    private String descripcion;

    @NotNull(message="El monto es obligatorio")
    @Positive(message="El monto debe ser mayor a cero")
    private Double monto;

    @NotNull(message="Debes seleccionar un tipo")
    private TipoTransaccion tipo;

    @NotNull(message="Debes tener un tipo de estos")
    private CategoriaKakeibo categoria;

    //Getters y Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    public Double getMonto() {return monto; }
    public void setMonto(Double monto){this.monto = monto;}

    public TipoTransaccion getTipo() { return tipo; }
    public void setTransaccion(TipoTransaccion tipo) {this.tipo = tipo;}

    public CategoriaKakeibo getCategoria() { return categoria; }
    public void setCategoria(CategoriaKakeibo categoria) {this.categoria = categoria;}

}
