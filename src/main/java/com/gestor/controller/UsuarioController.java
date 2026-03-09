package com.gestor.controller;

import com.gestor.model.Usuario;
import com.gestor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping("/perfil")
    public String verPerfil(Model model, Principal principal) {
        Usuario usuario = usuarioRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam String nombre,
                                   @RequestParam("archivoFoto")MultipartFile archivo,
                                   Principal principal) throws IOException {
        Usuario usuario = usuarioRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //Actualizamos los datos
        usuario.setNombre(nombre);

        if(!archivo.isEmpty()){
            //1.Definir la ruta de la carpeta
            String rutaAbsolutaStr = "/home/ronydex/Proyectos/GestorFinanzas/uploads/";
            Path directorioImagenes = Paths.get(rutaAbsolutaStr);

            //2.Creamos el directorio si no existe
            if (!Files.exists(directorioImagenes)){
                Files.createDirectories(directorioImagenes);
            }

            //3.Le damos un nombre unico al archivo para que nos sobreescriba.
            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path rutaCompleta = directorioImagenes.resolve(nombreArchivo);

            //4.Guardamos el archivo físicamente.
            Files.write(rutaCompleta, archivo.getBytes());

            //5.Guardamos la URL para que spring la use.
            usuario.setFotoUrl("/imagenes/" + nombreArchivo);
        }

        usuarioRepo.save(usuario); // Guarda en MYSQL
        return "redirect:/ver-todo?exito=perfilActualizado";
    }
}
