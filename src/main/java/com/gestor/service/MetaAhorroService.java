package com.gestor.service;

import com.gestor.repository.MetaAhorroRepository;
import com.gestor.repository.UsuarioRepository;
import com.gestor.dto.MetaAhorroRequestDTO;
import com.gestor.model.Usuario;
import com.gestor.model.MetaAhorro;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserNameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class MetaAhorroService {
   
    private final UsuarioRepository usuarioRepo;
    private final MetaAhorroRepository metaAhorroRepo;

    public MetaAhorroService(UsuarioRepository usuarioRepo, MetaAhorroRepository metaAhorroRepo) {
        this.usuarioRepo = usuarioRepo;
        this.metaAhorroRepo = metaAhorroRepo;
    }

    public void guardarOActualizarMeta(MetaAhorroRequestDTO metAhorroDTO, String email){
        Usuario usuario = usuarioRepo.findByEmail(email)
        .orElseThrow(() -> new  UsernameNotFoundException("Usuario No Encontrado" + email));
        Optional<MetaAhorro> metaExistenteOpt = metaAhorroRepo.findByUsuarioEmailAndMesAnio(email, metAhorroDTO.getMesAnio());

        if(metaExistenteOpt.isPresent()){
            MetaAhorro metaActualizar = metaExistenteOpt.get();
            metaActualizar.setMontoObjetivo(metAhorroDTO.getMontoObjetivo());
            metaAhorroRepo.save(metaActualizar);
        }
        else{
            MetaAhorro nuevaMeta = new MetaAhorro();
            nuevaMeta.setMontoObjetivo(metAhorroDTO.getMontoObjetivo());
            nuevaMeta.setMesAnio(metAhorroDTO.getMesAnio());
            nuevaMeta.setUsuario(usuario);
            metaAhorroRepo.save(nuevaMeta);
        }
    }


}
