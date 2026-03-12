package com.insper.mini_spotify_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class UsuarioController {

    //POST /usuarios
    //GET /usuarios
    //GET /usuarios/{id}
    //PUT /usuarios/{id}
    //DELETE /usuarios/{id}

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario postUsuario(@RequestBody Usuario usuario) {
        return usuarioService.cadastrarUsuario(usuario);
    }


    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

}
