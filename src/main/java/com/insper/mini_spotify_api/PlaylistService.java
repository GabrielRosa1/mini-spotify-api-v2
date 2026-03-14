package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PlaylistService {

    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final HashMap<UUID, Playlist> playlists = new HashMap<>();

    public PlaylistService(UsuarioService usuarioService, MusicaService musicaService) {
        this.usuarioService = usuarioService;
        this.musicaService = musicaService;
    }

    // POST /playlists
    public Playlist criarPlaylist(Playlist playlist) {

        if (playlist == null) {
            throw new RuntimeException("Body inválido");
        }

        if (playlist.getNome() == null || playlist.getNome().isBlank()) {
            throw new RuntimeException("Nome da playlist é obrigatório");
        }

        if (playlist.getUsuario() == null || playlist.getUsuario().getId() == null) {
            throw new RuntimeException("Usuário é obrigatório");
        }

        if (!usuarioService.verifyUUID(playlist.getUsuario().getId())) {
            throw new RuntimeException("Id de um usuário válido é obrigatório");
        }

        Usuario usuario = usuarioService.getUsuario(playlist.getUsuario().getId());

        if (!usuario.isAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        if (playlist.getPublica() == null) {
            throw new RuntimeException("Campo 'publica' é obrigatório");
        }

        for (Playlist p : playlists.values()) {
            if (p.getNome() != null && p.getNome().equalsIgnoreCase(playlist.getNome())) {
                throw new RuntimeException("Já existe uma playlist com esse nome");
            }
        }

        List<Musica> musicasReais = new ArrayList<>();

        if (playlist.getMusicas() != null) {
            for (Musica musica : playlist.getMusicas()) {
                if (musica == null || musica.getId() == null) {
                    throw new RuntimeException("A playlist contém música inválida");
                }

                Musica musicaReal = musicaService.getMusica(musica.getId());

                boolean repetida = false;
                for (Musica m : musicasReais) {
                    if (m.getId().equals(musicaReal.getId())) {
                        repetida = true;
                        break;
                    }
                }

                if (!repetida) {
                    musicasReais.add(musicaReal);
                }
            }
        }

        playlist.setId(UUID.randomUUID());
        playlist.setUsuario(usuario);
        playlist.setMusicas(musicasReais);
        playlist.setDataCriacao(LocalDateTime.now());
        playlist.setAtivo(true);

        playlists.put(playlist.getId(), playlist);
        return playlist;
    }

    // GET /playlists
    public Collection<Playlist> getPlaylists() {
        Collection<Playlist> totalPlaylists = new ArrayList<>();

        for (Playlist p : playlists.values()) {
            if (p.getNome() != null && p.isAtivo()) {
                totalPlaylists.add(p);
            }
        }

        return totalPlaylists;
    }

    // GET /playlists/{id}
    public Playlist getPlaylist(UUID id) {
        Playlist playlist = playlists.get(id);

        if (playlist == null || !playlist.isAtivo()) {
            throw new RuntimeException("Essa playlist não existe");
        }

        if (playlist.getMusicas() == null) {
            playlist.setMusicas(new ArrayList<>());
        }

        return playlist;
    }

    // PUT /playlists/{id}
    public Playlist editPlaylist(UUID id, Playlist dadosAtualizados) {

        Playlist playlist = playlists.get(id);

        if (playlist == null || !playlist.isAtivo()) {
            throw new RuntimeException("Essa playlist não existe ou não pode ser modificada");
        }

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        if (dadosAtualizados.getNome() == null || dadosAtualizados.getNome().isBlank()) {
            throw new RuntimeException("Nome da playlist é obrigatório");
        }

        if (dadosAtualizados.getUsuario() == null || dadosAtualizados.getUsuario().getId() == null) {
            throw new RuntimeException("Usuário é obrigatório");
        }

        if (!usuarioService.verifyUUID(dadosAtualizados.getUsuario().getId())) {
            throw new RuntimeException("Id de um usuário válido é obrigatório");
        }

        Usuario usuario = usuarioService.getUsuario(dadosAtualizados.getUsuario().getId());

        if (!usuario.isAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        if (dadosAtualizados.getPublica() == null) {
            throw new RuntimeException("Campo 'publica' é obrigatório");
        }

        for (Playlist p : playlists.values()) {
            if (!p.getId().equals(id)
                    && p.getNome() != null
                    && p.getNome().equalsIgnoreCase(dadosAtualizados.getNome())) {
                throw new RuntimeException("Já existe uma playlist com esse nome");
            }
        }

        List<Musica> musicasReais = new ArrayList<>();

        if (dadosAtualizados.getMusicas() != null) {
            for (Musica musica : dadosAtualizados.getMusicas()) {
                if (musica == null || musica.getId() == null) {
                    throw new RuntimeException("A playlist contém música inválida");
                }

                Musica musicaReal = musicaService.getMusica(musica.getId());

                boolean repetida = false;
                for (Musica m : musicasReais) {
                    if (m.getId().equals(musicaReal.getId())) {
                        repetida = true;
                        break;
                    }
                }

                if (!repetida) {
                    musicasReais.add(musicaReal);
                }
            }
        }

        playlist.setNome(dadosAtualizados.getNome());
        playlist.setUsuario(usuario);
        playlist.setPublica(dadosAtualizados.getPublica());
        playlist.setMusicas(musicasReais);

        return playlist;
    }

    // DELETE /playlists/{id}
    public void deletePlaylist(UUID id) {
        Playlist playlist = playlists.get(id);

        if (playlist == null || !playlist.isAtivo()) {
            throw new RuntimeException("Essa playlist não existe ou não pode ser modificada");
        }

        playlist.setAtivo(false);
    }

    // PUT /playlists/reativar/{id}
    public Playlist reactivatePlaylist(UUID id) {
        Playlist playlist = playlists.get(id);

        if (playlist == null) {
            throw new RuntimeException("Essa playlist não existe ou não pode ser modificada");
        }

        if (playlist.isAtivo()) {
            throw new RuntimeException("Essa playlist já está ativa");
        }

        playlist.setAtivo(true);
        return playlist;
    }

    // POST /playlists/{playlistId}/musicas/{musicaId}
    public Playlist adicionarMusica(UUID pId, UUID mId, UUID usuarioId) {
        Playlist playlist = playlists.get(pId);

        if (playlist == null || !playlist.isAtivo()) {
            throw new RuntimeException("Essa playlist não existe");
        }

        if (playlist.getMusicas() == null) {
            playlist.setMusicas(new ArrayList<>());
        }

        Musica musica = musicaService.getMusica(mId);
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        if (!playlist.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Apenas o dono da playlist pode adicionar músicas");
        }

        for (Musica m : playlist.getMusicas()) {
            if (m.getId().equals(musica.getId())) {
                throw new RuntimeException("Essa música já está na playlist");
            }
        }

        playlist.getMusicas().add(musica);
        return playlist;
    }

    public boolean verifyUUID(UUID id) {
        for (Playlist p : playlists.values()) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
