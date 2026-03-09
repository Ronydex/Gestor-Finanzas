package com.gestor.repository;

import com.gestor.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long>{
	List<Gasto> findByUsuarioEmail(String email);
}


