package com.insper.mini_spotify_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Object> postUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario user = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Usuario>> getUsuarios() {
        Collection<Usuario> totalUsuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(totalUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable UUID id) {
        try {
            Usuario usuario = usuarioService.getUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editUsuario(@PathVariable UUID id, @RequestBody Usuario dadosAtualizados) {
        try {
            Usuario usuarioAtualizado = usuarioService.editUsuario(id, dadosAtualizados);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable UUID id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<Object> reactivateUsuario(@PathVariable UUID id) {
        try {
            Usuario usuario = usuarioService.reactivateUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}