package com.gestor.repository;

import com.gestor.model.Transaccion;
import com.gestor.model.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
	
	//Método para encontrar transacciones de un usuario específico
	List<Transaccion> findByUsuarioIdUsuario(Long idUsuario);
	
	//Consulta personalizada para sumar ingresos del mes actual
	@Query("SELECT SUM(t.monto) FROM Transaccion t WHERE t.usuario.idUsuario = ?1 " +
		   "AND t.tipo = ?2 AND MONTH(t.fecha) = MONTH(CURRENT_DATE) " +
		   "AND YEAR(t.fecha) = YEAR(CURRENT_DATE)")
		   
	BigDecimal sumarMontoPorTipoYMes(Long idUsuario, TipoTransaccion tipo);
	}
