package com.gestor.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class GlobalExceptionHandler {

    //1.Capturar nuestra excepcion personalizada (Recurso No Encontrado)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String manejarResourceNotFound(ResourceNotFoundException ex, RedirectAttributes redirectAttributes){
        //Redirigimos al dashboard enviando el mensaje de error de forma segura
        redirectAttributes.addFlashAttribute("errorMensaje", ex.getMessage());
        return "redirect:/ver-todo"; //O la ruta principal en el dashboard
    }

    //2.Capturar CUALQUIER otro error inesperado del sistema (Efecto Red de Seguridad)
    @ExceptionHandler(Exception.class)
    public String manejarErroresGenerales(Exception ex, Model model) {
        //Para errores graves del sistema, podemos mandarlo a una vista dedicada de error
        model.addAttribute("errorCodigo", 500);
        model.addAttribute("errorTitulo", "Error Interno del Servidor");
        model.addAttribute("errorDetalle","Ocurrió un problema inesperado en el backend: " + ex.getMessageL());
        return "error"; //Apunta a un archivo error.html que crearemos
    }
}
