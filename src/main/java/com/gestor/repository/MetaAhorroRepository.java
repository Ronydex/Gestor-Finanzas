package com.gestor.repository;

import com.gestor.model.MetaAhorro;
import org.springframework.data.jpa.repository.JpaRepository;
import org,springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaAhorro extends JpaRepository<MetaAhorro, Long>{
    
    //Éste método nos permitirá extraer la meta de un mes específico usando el correo de la sesión activa.
    Optional<MetaAhorro> findByUsuarioEmailAndMesAnio(String email, String mesAnio);
}
