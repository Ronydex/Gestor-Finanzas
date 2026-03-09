package com.gestor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Mapeamos la URL a la carpeta física uploads/
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:///home/ronydex/Proyectos/GestorFinanzas/uploads/");
    }
}
