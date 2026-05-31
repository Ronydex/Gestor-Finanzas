package com.gestor.dto;

import com.gestor.model.MetaAhorro;
import javax.validation.constraints;

public class MetaAhorroRequestDTO {
    
    @NotNull(message= "El monto es obligatorio")
    private Double montoObjetivo;
    @NotBlank(message="El mes y anio no debe estar vacío")
    private String mesAnio;

    //Constructor vacío
    public MetaAhorroRequestDTO(){}


    //Constructor con parámetros
    public MetaAhorroRequestDTO(Double montoObjetivo, String mesAnio){
    this.montoObjetivo = montoObjetivo;
    this.mesAnio = mesAnio;
    }

    //Getters y Setters
    public Double getMontoObjetivo(){ return montoObjetivo; }
    public void setMontoObjetivo(Double montoObjetivo){ this.montoObjetivo = montoObjetivo;}

    public String getMesAnio(){ return mesAnio; }
    public void setMesAnio(String mesAnio){ this.mesAnio = mesAnio; }
}
