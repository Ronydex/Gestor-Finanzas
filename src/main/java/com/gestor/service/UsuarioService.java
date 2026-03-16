package com.gestor.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestor.model.Usuario;
import com.gestor.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.ArrayList;

@Service
public class UsuarioService implements UserDetailsService {

    private  final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final String UPLOAD_DIR = "/home/ronydex/Proyectos/GestorFinanzas/uploads/";

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
        //Buscar al usuario por email del repositorio  que esta alojado en la DB.
        Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        //Retornamos un objeto de Spring Security con los datos de la tabla:
        return new User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
    }

    public void guardar(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public void actualizarPerfil(Usuario usuario, org.springframework.web.multipart.MultipartFile imagen) throws java.io.IOException {
        if (imagen != null && !imagen.isEmpty()) {
            String nombreImagen = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
            java.nio.file.Path ruta = java.nio.file.Paths.get(UPLOAD_DIR + nombreImagen);

            java.nio.file.Files.write(ruta, imagen.getBytes());

            usuario.setFotoUrl("/imagenes/" + nombreImagen);
        }
        usuarioRepository.save(usuario);
    }
}
