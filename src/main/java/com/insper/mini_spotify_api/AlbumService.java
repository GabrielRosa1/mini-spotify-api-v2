package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class AlbumService {

    private final MusicaService musicaService;
    private final ArtistaService artistaService;
    private final HashMap<UUID, Album> albuns = new HashMap<>();

    public AlbumService(MusicaService musicaService, ArtistaService artistaService) {
        this.musicaService = musicaService;
        this.artistaService = artistaService;
    }

    public HashMap<UUID, Album> getAlbuns() {
        return albuns;
    }

    //POST /albuns
    public Album criarAlbum(Album album) {

        if (album == null) {
            throw new RuntimeException("Body inválido");
        }

        if (album.getTitulo() == null || album.getTitulo().isBlank()) {
            throw new RuntimeException("Título do album é obrigatório");
        }

        List<Musica> musicasReais = new ArrayList<>();
        if (!album.getMusicas().isEmpty()) {
            for (Musica a : album.getMusicas()) {
                for (Musica b : musicaService.getHashMusicas().values()) {
                    if (a.getId().equals(b.getId())) {
                        musicasReais.add(a);
                    }
                }
            }
        }

        if (album.getArtista() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (!artistaService.verifyUUID(album.getArtista().getId())) {
            throw new RuntimeException("Id de um usuário válido é obrigatório");
        }

        if (!artistaService.getArtista(album.getArtista().getId()).isAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        album.setId(UUID.randomUUID());
        album.setAtivo(true);
        album.setDataLancamento(LocalDate.now());
        album.setMusicas(musicasReais);

        albuns.put(album.getId(), album);
        return album;
    }

    //GET /albuns
    public Collection<Album> getTotalAlbuns() {

        Collection<Album> totalAlbuns = new ArrayList<>();

        for (Album a : albuns.values()) {
            if (a.getTitulo() != null && a.isAtivo()) {
                totalAlbuns.add(a);
            }
        }

        return totalAlbuns;
    }

    //GET /albuns/{id}
    public Album getAlbum(UUID id) {

        Album album = albuns.get(id);

        if (album == null || !album.isAtivo()) {
            throw new RuntimeException("Esse album não existe");
        }

        return album;

    }

    //PUT /albuns/{id}
    public Album editAlbum(UUID id, Album dadosAtualizados) {

        Album album = albuns.get(id);

        if (album == null || !album.isAtivo()) {
            throw new RuntimeException("Essa album não existe ou não pode ser modificado");
        }

        if (dadosAtualizados.getTitulo().isBlank() || dadosAtualizados.getTitulo() == null) {
            throw new RuntimeException("Título do album é obrigatório");
        }

        List<Musica> musicasReaisUpdate = new ArrayList<>();
        if (!dadosAtualizados.getMusicas().isEmpty()) {
            for (Musica a : dadosAtualizados.getMusicas()) {
                for (Musica b : musicaService.getHashMusicas().values()) {
                    if (a.getId().equals(b.getId())) {
                        musicasReaisUpdate.add(a);
                    }
                }
            }
            album.setMusicas(musicasReaisUpdate);
        }

        if (dadosAtualizados.getArtista() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (!artistaService.verifyUUID(dadosAtualizados.getArtista().getId())) {
            throw new RuntimeException("Id de um artista válido é obrigatório");
        }

        if (!artistaService.getArtista(dadosAtualizados.getArtista().getId()).isAtivo()) {
            throw new RuntimeException("Artista inativo");
        }


        album.setTitulo(dadosAtualizados.getTitulo());
        album.setArtista(dadosAtualizados.getArtista());
        album.setMusicas(musicasReaisUpdate);

        return album;
    }

    //DELETE /albuns/{id}
    public void deleteAlbum(UUID id) {
        Album album = albuns.get(id);

        if (album == null || !album.isAtivo()) {
            throw new RuntimeException("Essa album não existe ou não pode ser modificada");
        }

        album.setAtivo(false);
    }

    //PUT /albuns/reativar/{id}
    public Album reactivateAlbum(UUID id) {
        Album album = albuns.get(id);

        if (album == null) {
            throw new RuntimeException("Esse album não existe ou não pode ser modificado");
        }

        if (album.isAtivo()) {
            throw new RuntimeException("Esse album já está ativo");
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
