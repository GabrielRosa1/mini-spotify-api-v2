package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public Artista criarArtista(Artista artista) {
        if (artista == null) {
            throw new RuntimeException("Body inválido");
        }

        if (artista.getNome() == null || artista.getNome().isBlank()) {
            throw new RuntimeException("Nome do artista é obrigatório");
        }

        if (artista.getGeneroMusical() == null || artista.getGeneroMusical().isBlank()) {
            throw new RuntimeException("Gênero musical do artista é obrigatório");
        }

        if (artista.getPaisOrigem() == null || artista.getPaisOrigem().isBlank()) {
            throw new RuntimeException("País de origem do artista é obrigatório");
        }

        if (artistaRepository.existsByNomeIgnoreCase(artista.getNome())) {
            throw new RuntimeException("Já existe um artista com esse nome");
        }

        artista.setId(null);
        artista.setAtivo(true);

        prepararAlbuns(artista);

        return artistaRepository.save(artista);
    }

    public Collection<Artista> getArtistas() {
        return artistaRepository.findAllByAtivoTrue();
    }

    public Artista getArtista(UUID id) {
        return artistaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse artista não existe"));
    }

    public Artista editArtista(UUID id, Artista dadosAtualizados) {
        Artista artista = artistaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse artista não existe ou não pode ser modificado"));

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        if (dadosAtualizados.getNome() == null || dadosAtualizados.getNome().isBlank()) {
            throw new RuntimeException("Nome do artista é obrigatório");
        }

        if (dadosAtualizados.getGeneroMusical() == null || dadosAtualizados.getGeneroMusical().isBlank()) {
            throw new RuntimeException("Gênero musical do artista é obrigatório");
        }

        if (dadosAtualizados.getPaisOrigem() == null || dadosAtualizados.getPaisOrigem().isBlank()) {
            throw new RuntimeException("País de origem do artista é obrigatório");
        }

        if (artistaRepository.existsByNomeIgnoreCaseAndIdNot(dadosAtualizados.getNome(), id)) {
            throw new RuntimeException("Já existe um artista com esse nome");
        }

        artista.setNome(dadosAtualizados.getNome());
        artista.setGeneroMusical(dadosAtualizados.getGeneroMusical());
        artista.setPaisOrigem(dadosAtualizados.getPaisOrigem());

        if (dadosAtualizados.getAlbuns() != null) {
            artista.setAlbuns(dadosAtualizados.getAlbuns());
        }

        prepararAlbuns(artista);

        return artistaRepository.save(artista);
    }

    public void deleteArtista(UUID id) {
        Artista artista = artistaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse artista não existe ou não pode ser modificado"));

        artista.setAtivo(false);
        artistaRepository.save(artista);
    }

    public Artista reactivateArtista(UUID id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esse artista não existe ou não pode ser modificado"));

        if (artista.isAtivo()) {
            throw new RuntimeException("Esse artista já está ativo");
        }

        artista.setAtivo(true);
        return artistaRepository.save(artista);
    }

    public boolean verifyUUID(UUID id) {
        return artistaRepository.existsById(id);
    }

    private void prepararAlbuns(Artista artista) {
        if (artista.getAlbuns() == null) {
            artista.setAlbuns(new ArrayList<>());
            return;
        }

        for (Album album : artista.getAlbuns()) {
            if (album == null) {
                throw new RuntimeException("Lista de álbuns contém item inválido");
            }

            album.setArtista(artista);

            if (album.getMusicas() == null) {
                album.setMusicas(new ArrayList<>());
            }

            if (!album.isAtivo()) {
                album.setAtivo(true);
            }

            for (Musica musica : album.getMusicas()) {
                if (musica == null) {
                    throw new RuntimeException("Lista de músicas contém item inválido");
                }

                musica.setAlbum(album);
                musica.setArtista(artista);

                if (!musica.isAtivo()) {
                    musica.setAtivo(true);
                }
            }
        }
    }
}