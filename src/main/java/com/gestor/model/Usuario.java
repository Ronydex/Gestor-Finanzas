package com.gestor.model;

import javax.persistence.*;
import java.time.LocalDateTime;

//Mi entidad de java para Base de datos:
//En este creo la Table con la anotación @Table llamada "usuarios"
@Entity
@Table(name = "usuarios")

//Clase con todas las columnas que tendra la tabla "usuarios"
public class Usuario{

	//La anotación @Id significa que será el identificador único de una entidad
	@Id
	//La parte @GeneratedValue significa que se generará automáticamente los valores de la clave primaria.
	//Por otra parte,GenerationType.IDENTITY significa que el valor de la clave primaria se generá automaticamente por la base de datos,mediante una columna de autoincremento
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;

	//La anotación @Column define la columna con sus respectivas caracteristicas,en este caso dice lo sig:
	//No debe ser nullable,es decir no estar vacio y define que su extension maxima es de 100 caracteres
	@Column(nullable = false, length = 100)
	private String nombre;

	//No debe ser nullable,es decir no estar vacio y define que su extension maxima es de 100 caracteres,ademas de que debe ser unico
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	//No debe ser nullable,es decir no estar vacio y define que su extension maxima es de 255 caracteres
	@Column(nullable = false, length = 255)
	private String password;

	private String fotoUrl;

	//No debe ser modificable y su nombre es de fecha_registro,ademas de que la fecha y hora corresponde al momento que se escribio(LocalDateTime.now)
	@Column(name = "fecha_registro", updatable = false)
	private LocalDateTime fechaRegistro = LocalDateTime.now();


	//Getters y Setters
	public Long getIdUsuario() { return idUsuario; }
	public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
	
	public String getNombre() { return nombre; }
	public void setNombre (String nombre) { this.nombre = nombre; }
	
	public String getEmail() { return email; }
	public void setEmail (String email) { this.email = email; }
	
	public String getPassword() { return password; }
	public void setPassword (String password) { this.password = password; }

	public String getFotoUrl() {return fotoUrl;}
	public void setFotoUrl(String fotoUrl) {this.fotoUrl = fotoUrl;}
}
