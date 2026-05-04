package com.gestor.dto;

import com.gestor.model.TipoTransaccion;
import javax.validation.constraints.*;

public class GastoDTO{

    @NotBlank(message="La descripcion no debe estar vacia")
    private String descripcion;

    @NotNull(message="El monto es obligatorio")
    @Positive(message="El monto debe ser mayor a cero")
    private Double monto;

    @NotNull(message="Debes seleccionar un tipo")
    private TipoTransaccion tipo;


    //Getters y Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    public Double getMonto() {return monto; }
    public void setMonto(Double monto){this.monto = monto;}

    public TipoTransaccion getTipo() { return tipo; }
    public void setTransaccion(TipoTransaccion Tipo) {this.tipo = tipo;}

}
