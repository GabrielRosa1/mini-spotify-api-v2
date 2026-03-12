package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsuarioService {

    //GET /usuarios
    //GET /usuarios/{id}
    //PUT /usuarios/{id}
    //DELETE /usuarios/{id}

    private HashMap<UUID, Usuario> usuarios = new HashMap<>();

    //Espaço para @Autowired

    //POST /usuarios
    public Usuario cadastrarUsuario(Usuario usuario) {

        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equals(usuario.getEmail())) {
                throw new RuntimeException("Um usuário com esse e-mail já existe");
            }
        }

        if (usuario.getNome() == null || usuario.getEmail() == null || usuario.getTipoPlano() == null) {
            throw new RuntimeException("Dados incompletos");
        }

        usuario.setId(UUID.randomUUID());
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public Collection<Usuario> getUsuarios() {

        Collection<Usuario> totalUsuarios = new ArrayList<>();

        for (Usuario u : usuarios.values()) {
            if (u.getEmail() != null) {
                totalUsuarios.add(u);
            }
        }

        return totalUsuarios;
    }

}
