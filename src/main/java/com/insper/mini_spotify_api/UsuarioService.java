package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsuarioService {

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

    //GET /usuarios
    public Collection<Usuario> getUsuarios() {

        Collection<Usuario> totalUsuarios = new ArrayList<>();

        for (Usuario u : usuarios.values()) {
            if (u.getEmail() != null) {
                totalUsuarios.add(u);
            }
        }

        return totalUsuarios;
    }

    //GET /usuarios/{id}
    public Usuario getUsuario(UUID id) {

        Usuario usuario = usuarios.get(id);

        if (usuario == null) {
            throw new RuntimeException("Esse usuário não existe");
        }

        return usuario;

    }

    //PUT /usuarios/{id}
    public Usuario editUsuario(UUID id, String nome, String email, Usuario.TipoPlano tipoPlano) {

        Usuario usuario = usuarios.get(id);

        if (usuario == null || !usuario.isAtivo()) {
            throw new RuntimeException("Esse usuário não existe ou não pode ser modificado");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTipoPlano(tipoPlano);

        return usuario;

    }

    //DELETE /usuarios/{id}
    public void deleteUsuario(UUID id) {

        Usuario usuario = usuarios.get(id);
        usuario.setAtivo(false);

    }

}
