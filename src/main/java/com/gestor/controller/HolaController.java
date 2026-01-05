package com.gestor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/")
    public String inicio() {
        return "<h1>¡Servidor de Finanzas Activo!</h1><p>El motor Spring Boot está funcionando correctamente en tu Core i3.</p>";
    }
}
