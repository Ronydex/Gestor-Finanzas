package com.gestor.model;

//Se usa el siguiente enum con el objetivo de que solo haya INGRESO y EGRESO de la respectiva tabla,asi se conserva la integridad de los datos.
//También se evitan errores de escritura en el código,además de que sirve como forma de tener una lista "cerrada" de opciones,sin afectar a las propias transacciones
public enum TipoTransaccion {
	INGRESO,
	EGRESO,
	GASTO
	}
