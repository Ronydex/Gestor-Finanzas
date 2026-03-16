package com.gestor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Esto permite que cualquier URL que empiece con /imagenes/
        //la buscará en tu carpeta física uploads
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:/home/ronydex/Proyectos/GestorFinanzas/uploads/");
    }
}
