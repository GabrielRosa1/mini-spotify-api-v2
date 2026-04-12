package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class AlbumService {

    private final ArtistaService artistaService;
    private final AlbumRepository albumRepository;

    public AlbumService(ArtistaService artistaService, AlbumRepository albumRepository) {
        this.artistaService = artistaService;
        this.albumRepository = albumRepository;
    }

    public Album criarAlbum(Album album) {
        if (album == null) {
            throw new RuntimeException("Body inválido");
        }

        if (album.getTitulo() == null || album.getTitulo().isBlank()) {
            throw new RuntimeException("Título do álbum é obrigatório");
        }

        if (album.getArtista() == null || album.getArtista().getId() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (!artistaService.verifyUUID(album.getArtista().getId())) {
            throw new RuntimeException("Id de um artista válido é obrigatório");
        }

        Artista artista = artistaService.getArtista(album.getArtista().getId());

        if (!artista.isAtivo()) {
            throw new RuntimeException("Artista inativo");
        }

        if (albumRepository.existsByTituloIgnoreCase(album.getTitulo())) {
            throw new RuntimeException("Já existe um álbum com esse título");
        }

        album.setId(null);
        album.setArtista(artista);
        album.setAtivo(true);
        album.setDataLancamento(LocalDate.now());

        prepararMusicas(album);

        return albumRepository.save(album);
    }

    public Collection<Album> getTotalAlbuns() {
        return albumRepository.findAllByAtivoTrue();
    }

    public Album getAlbum(UUID id) {
        return albumRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse álbum não existe"));
    }

    public Album editAlbum(UUID id, Album dadosAtualizados) {
        Album album = albumRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse álbum não existe ou não pode ser modificado"));

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

        if (albumRepository.existsByTituloIgnoreCaseAndIdNot(dadosAtualizados.getTitulo(), id)) {
            throw new RuntimeException("Já existe um álbum com esse título");
        }

        album.setTitulo(dadosAtualizados.getTitulo());
        album.setArtista(artista);

        if (dadosAtualizados.getDataLancamento() != null) {
            album.setDataLancamento(dadosAtualizados.getDataLancamento());
        }

        if (dadosAtualizados.getMusicas() != null) {
            album.setMusicas(dadosAtualizados.getMusicas());
        }

        prepararMusicas(album);

        return albumRepository.save(album);
    }

    public void deleteAlbum(UUID id) {
        Album album = albumRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse álbum não existe ou não pode ser modificado"));

        album.setAtivo(false);
        albumRepository.save(album);
    }

    public Album reactivateAlbum(UUID id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esse álbum não existe ou não pode ser modificado"));

        if (album.isAtivo()) {
            throw new RuntimeException("Esse álbum já está ativo");
        }

        album.setAtivo(true);
        return albumRepository.save(album);
    }

    public boolean verifyUUID(UUID id) {
        return albumRepository.existsById(id);
    }

    private void prepararMusicas(Album album) {
        if (album.getMusicas() == null) {
            album.setMusicas(new ArrayList<>());
            return;
        }

        for (Musica musica : album.getMusicas()) {
            if (musica == null) {
                throw new RuntimeException("Lista de músicas contém item inválido");
            }

            if (musica.getTitulo() == null || musica.getTitulo().isBlank()) {
                throw new RuntimeException("Toda música do álbum precisa ter título");
            }

            if (musica.getNumeroFaixa() < 0 || musica.getDuracaoSegundos() < 0) {
                throw new RuntimeException("Campos numeroFaixa e duracaoSegundos devem ser maiores ou iguais a zero");
            }

            musica.setAlbum(album);
            musica.setArtista(album.getArtista());

            if (!musica.isAtivo()) {
                musica.setAtivo(true);
            }
        }
    }
}