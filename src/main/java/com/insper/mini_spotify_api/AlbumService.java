package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AlbumService {

    private final ArtistaService artistaService;
    private final HashMap<UUID, Album> albuns = new HashMap<>();

    public AlbumService(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    public HashMap<UUID, Album> getAlbuns() {
        return albuns;
    }

    public Album criarAlbum(Album album) {
        if (album == null) {
            throw new RuntimeException("Body inválido");
        }

        if (album.getTitulo() == null || album.getTitulo().isBlank()) {
            throw new RuntimeException("Título do álbum é obrigatório");
        }

        if (album.getArtista() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (!artistaService.verifyUUID(album.getArtista().getId())) {
            throw new RuntimeException("Id de um artista válido é obrigatório");
        }

        if (!artistaService.getArtista(album.getArtista().getId()).isAtivo()) {
            throw new RuntimeException("Artista inativo");
        }

        album.setId(UUID.randomUUID());
        album.setAtivo(true);
        album.setDataLancamento(LocalDate.now());

        if (album.getMusicas() == null) {
            album.setMusicas(new ArrayList<>());
        }

        albuns.put(album.getId(), album);
        return album;
    }

    public Collection<Album> getTotalAlbuns() {
        Collection<Album> totalAlbuns = new ArrayList<>();

        for (Album a : albuns.values()) {
            if (a.getTitulo() != null && a.isAtivo()) {
                totalAlbuns.add(a);
            }
        }

        return totalAlbuns;
    }

    public Album getAlbum(UUID id) {
        Album album = albuns.get(id);

        if (album == null || !album.isAtivo()) {
            throw new RuntimeException("Esse álbum não existe");
        }

        return album;
    }

    public Album editAlbum(UUID id, Album dadosAtualizados) {
        Album album = albuns.get(id);

        if (album == null || !album.isAtivo()) {
            throw new RuntimeException("Esse álbum não existe ou não pode ser modificado");
        }

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        if (dadosAtualizados.getTitulo() == null || dadosAtualizados.getTitulo().isBlank()) {
            throw new RuntimeException("Título do álbum é obrigatório");
        }

        if (dadosAtualizados.getArtista() == null || dadosAtualizados.getArtista().getId() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (!artistaService.verifyUUID(dadosAtualizados.getArtista().getId())) {
            throw new RuntimeException("Id de um artista válido é obrigatório");
        }

        Artista artista = artistaService.getArtista(dadosAtualizados.getArtista().getId());

        if (!artista.isAtivo()) {
            throw new RuntimeException("Artista inativo");
        }

        album.setTitulo(dadosAtualizados.getTitulo());
        album.setArtista(artista);

        return album;
    }

    public void deleteAlbum(UUID id) {
        Album album = albuns.get(id);

        if (album == null || !album.isAtivo()) {
            throw new RuntimeException("Esse álbum não existe ou não pode ser modificado");
        }

        album.setAtivo(false);
    }

    public Album reactivateAlbum(UUID id) {
        Album album = albuns.get(id);

        if (album == null) {
            throw new RuntimeException("Esse álbum não existe ou não pode ser modificado");
        }

        if (album.isAtivo()) {
            throw new RuntimeException("Esse álbum já está ativo");
        }

        album.setAtivo(true);
        return album;
    }

    public boolean verifyUUID(UUID id) {
        for (Album a : albuns.values()) {
            if (a.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
