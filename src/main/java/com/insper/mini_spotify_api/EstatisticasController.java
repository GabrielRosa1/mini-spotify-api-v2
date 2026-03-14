package com.insper.mini_spotify_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class EstatisticasController {

    @Autowired
    private EstatisticasService estatisticasService;

    @PostMapping("/usuarios/{usuarioId}/estatisticas")
    public ResponseEntity<Object> criarEstatisticas(@PathVariable UUID usuarioId) {
        try {
            Estatisticas estatisticas = estatisticasService.criarEstatisticas(usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(estatisticas);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios/{usuarioId}/estatisticas")
    public ResponseEntity<Object> getEstatisticas(@PathVariable UUID usuarioId) {
        try {
            Estatisticas estatisticas = estatisticasService.getEstatisticas(usuarioId);
            return ResponseEntity.ok(estatisticas);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/usuarios/{usuarioId}/estatisticas")
    public ResponseEntity<Object> atualizarEstatisticas(@PathVariable UUID usuarioId, @RequestBody Estatisticas dadosAtualizados) {
        try {
            Estatisticas estatisticas = estatisticasService.atualizarEstatisticas(usuarioId, dadosAtualizados);
            return ResponseEntity.ok(estatisticas);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/usuarios/{usuarioId}/estatisticas")
    public ResponseEntity<Object> deleteEstatisticas(@PathVariable UUID usuarioId) {
        try {
            estatisticasService.deleteEstatisticas(usuarioId);
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
