package com.gestor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gestor.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long>{
    @Query("SELECT g FROM Gasto g WHERE g.usuario.email = :email")
	List<Gasto> buscarGastosPorEmailUsuario(@Param("email") String email);
}


