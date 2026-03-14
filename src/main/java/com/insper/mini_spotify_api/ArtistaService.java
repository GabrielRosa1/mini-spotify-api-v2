package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArtistaService {

    private final HashMap<UUID, Artista> artistas = new HashMap<>();

    public ArtistaService() {
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

        for (Artista a : artistas.values()) {
            if (a.getNome().equalsIgnoreCase(artista.getNome())) {
                throw new RuntimeException("Já existe um artista com esse nome");
            }
        }

        artista.setId(UUID.randomUUID());
        artista.setAtivo(true);

        if (artista.getAlbuns() == null) {
            artista.setAlbuns(new ArrayList<>());
        }

        artistas.put(artista.getId(), artista);
        return artista;
    }

    public Collection<Artista> getArtistas() {
        Collection<Artista> totalArtistas = new ArrayList<>();

        for (Artista a : artistas.values()) {
            if (a.getNome() != null && a.isAtivo()) {
                totalArtistas.add(a);
            }
        }

        return totalArtistas;
    }

    public Artista getArtista(UUID id) {
        Artista artista = artistas.get(id);

        if (artista == null || !artista.isAtivo()) {
            throw new RuntimeException("Esse artista não existe");
        }

        return artista;
    }

    public Artista editArtista(UUID id, Artista dadosAtualizados) {
        Artista artista = artistas.get(id);

        if (artista == null || !artista.isAtivo()) {
            throw new RuntimeException("Esse artista não existe ou não pode ser modificado");
        }

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

        for (Artista a : artistas.values()) {
            if (!a.getId().equals(id) && a.getNome().equalsIgnoreCase(dadosAtualizados.getNome())) {
                throw new RuntimeException("Já existe um artista com esse nome");
            }
        }

        artista.setNome(dadosAtualizados.getNome());
        artista.setGeneroMusical(dadosAtualizados.getGeneroMusical());
        artista.setPaisOrigem(dadosAtualizados.getPaisOrigem());

        if (dadosAtualizados.getAlbuns() != null) {
            artista.setAlbuns(dadosAtualizados.getAlbuns());
        }

        return artista;
    }

    public void deleteArtista(UUID id) {
        Artista artista = artistas.get(id);

        if (artista == null || !artista.isAtivo()) {
            throw new RuntimeException("Esse artista não existe ou não pode ser modificado");
        }

        artista.setAtivo(false);
    }

    public Artista reactivateArtista(UUID id) {
        Artista artista = artistas.get(id);

        if (artista == null) {
            throw new RuntimeException("Esse artista não existe ou não pode ser modificado");
        }

        if (artista.isAtivo()) {
            throw new RuntimeException("Esse artista já está ativo");
        }

        artista.setAtivo(true);
        return artista;
    }

    public boolean verifyUUID(UUID id) {
        for (Artista a : artistas.values()) {
            if (a.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
