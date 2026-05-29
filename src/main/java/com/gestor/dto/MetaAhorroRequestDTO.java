package com.gestor.dto;

import com.gestor.model.MetaAhorro;

public class MetaAhorroRequestDTO {

    private Double montoObjetivo;
    private String fechaCreacion;

    //Constructor vacío
    public MetaAhorroRequestDTO(){}


    //Constructor con parámetros
    public MetaAhorroRequestDTO(Double montoObjetivo, String fechaCreacion){
    this.montoObjetivo = montoObjetivo;
    this.fechaCreacion = fechaCreacion;
    }

    //Getters y Setters
    public Double getMontoObjetivo(){ return montoObjetivo; }
    public void setMontoObjetivo(Double montoObjetivo){ this.montoObjetivo = montoObjetivo;}

    public String getFechaCreacion(){ return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion){ this.fechaCreacion = fechaCreacion; }
}
