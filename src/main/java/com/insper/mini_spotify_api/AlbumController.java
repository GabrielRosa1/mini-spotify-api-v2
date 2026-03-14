package com.insper.mini_spotify_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;


@RestController
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @PostMapping("/albuns")
    public ResponseEntity<Object> criarAlbum(@RequestBody Album album) {
        try {
            Album m = albumService.criarAlbum(album);
            return ResponseEntity.status(HttpStatus.CREATED).body(m);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/albuns")
    public ResponseEntity<Collection<Album>> getAlbums() {

        Collection<Album> totalAlbums = albumService.getTotalAlbuns();
        return ResponseEntity.ok(totalAlbums);

    }

    @GetMapping("/albuns/{id}")
    public ResponseEntity<Object> getAlbum(@PathVariable UUID id) {
        try {
            Album album = albumService.getAlbum(id);
            return ResponseEntity.ok(album);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/albuns/{id}")
    public ResponseEntity<Object> editAlbum(@PathVariable UUID id, @RequestBody Album dadosAtualizados) {
        try {
            Album album = albumService.editAlbum(id, dadosAtualizados);
            return ResponseEntity.ok(album);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/albuns/{id}")
    public ResponseEntity<Object> deleteAlbum(@PathVariable UUID id) {
        try {
            albumService.deleteAlbum(id);
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/albuns/reativar/{id}")
    public ResponseEntity<Object> reactivateAlbum(@PathVariable UUID id) {
        try {
            Album album = albumService.reactivateAlbum(id);
            return ResponseEntity.ok(album);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
