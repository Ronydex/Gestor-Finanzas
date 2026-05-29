package com.gestor.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="metas_ahorro")

public class MetaAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double montoObjetivo;

    @Column(nullable = false, length = 7) //Formato seguro: "YYYY-MM"
    private String mesAnio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    //Constructores
    public MetaAhorro() {}

    public MetaAhorro(Double montoObjetivo, String mesAnio, Usuario usuario){
        this.montoObjetivo = montoObjetivo;
        this.mesAnio = mesAnio;
        this.usuario = usuario;
    }

    //Getters-Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id }

    public Double getMontoObjetivo() { return montoObjetivo;}
    public void setMontoObjetivo (Double montoObjetivo) { this.montoObjetivo = montoObjetivo; }

    public String getMesAnio() { return mesAnio; }
    public void setMesAnio (String mesAnio) { this.mesAnio = mesAnio; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario (Usuario usuario) { this.usuario= usuario; }

    public LocalDateTime getfechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion (LocalDateTime fechaCreacion) { this.fechaCreacion= fechaCreacion; }

}
