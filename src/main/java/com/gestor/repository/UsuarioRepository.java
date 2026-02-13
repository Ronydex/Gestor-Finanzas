package com.gestor.repository;

import com.gestor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	//Spring nos otorga métodos como save(), findAll(), delete() gracias a la interfaz que se ha generado
	//Nota:Agregar uno personalizado para el Login:
	Optional<Usuario> findByEmail(String email);
	}
