package com.gestor.service;

import com.gestor.dto.GastoResponseDTO;
import com.gestor.model.Gasto;
import com.gestor.model.TipoTransaccion;
import com.gestor.model.CategoriaKakeibo;
import com.gestor.repository.GastoRepository;
import com.gestor.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GastoServiceTest {
    
    @Mock
    private GastoRepository gastoRepo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMock
    private GastoService gastoService;

    private Gasto gastoMock;

    @BeforeEach
    void setUp(){
        //Este metodo se ejecuta ANTES de cada test para preparar datos limpios.
        gastoMock = new Gasto();
        gastoMock.setId(1L);
        gastoMock.setDescripcion("Quincena Mayo");
        gastoMock.setMonto("4500.0");
        gastoMock.setTipo(TipoTransaccion.INGRESO);
        gastoMock.setCategoria(CategoriaKakeibo.SUPERVIVENCIA);
        gastoMock.setFecha(LocalDate.now());
    }

   @Test
   @DisplayName("Debería retornar lista de DTOs filtrados correctamente por mes")
   void filtrarPorPeriodoMesExitoso(){
        //1.GIVEN
   }
}
