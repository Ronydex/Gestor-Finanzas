package com.gestor.controller;

import com.gestor.model.Usuario;
import com.gestor.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private com.gestor.service.UsuarioService usuarioService;

    @GetMapping("/perfil")
    public String verPerfil(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam String nombre,
                                   @RequestParam("archivoFoto")MultipartFile archivo,
                                   Principal principal) throws IOException {
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());

        usuario.setNombre(nombre);

        usuarioService.actualizarPerfil(usuario, archivo);

        return "redirect:/ver-todo?exito=perfilActualizado";
    }

}
