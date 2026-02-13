package com.gestor;

import com.gestor.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.gestor.repository.UsuarioRepository;


@SpringBootApplication
public class FinanzasApplication {
    
    public static void main(String[] args) {
        // Esta línea es la que arranca todo el framework
        SpringApplication.run(FinanzasApplication.class, args);

    }
        @Bean
        CommandLineRunner init(UsuarioRepository usuarioRepo, BCryptPasswordEncoder passwordEncoder){
            return args ->{
                //Creamos el usuario si la tabla esta vacía para evitar duplicados
                if(usuarioRepo.count() == 0) {
                    Usuario testUser = new Usuario();
                    testUser.setNombre("Testeo Rony");
                    testUser.setEmail("testeo@gmail.com");

                    //SE IMPORTA LA CONTRASEÑA DE PRUEBA "1234"
                    testUser.setPassword(passwordEncoder.encode("1234"));

                    usuarioRepo.save(testUser);
                    System.out.println("Usuario de prueba creado: testo@gmail.com / 1234");

                }
            };
        }
    }
