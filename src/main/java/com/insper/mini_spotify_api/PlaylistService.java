package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class PlaylistService {

    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final PlaylistRepository playlistRepository;

    public PlaylistService(
            UsuarioService usuarioService,
            MusicaService musicaService,
            PlaylistRepository playlistRepository
    ) {
        this.usuarioService = usuarioService;
        this.musicaService = musicaService;
        this.playlistRepository = playlistRepository;
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

        if (playlistRepository.existsByNomeIgnoreCase(playlist.getNome())) {
            throw new RuntimeException("Já existe uma playlist com esse nome");
        }

        List<Musica> musicasReais = resolverMusicas(playlist.getMusicas());

        playlist.setId(null);
        playlist.setUsuario(usuario);
        playlist.setMusicas(musicasReais);
        playlist.setDataCriacao(LocalDateTime.now());
        playlist.setAtivo(true);

        return playlistRepository.save(playlist);
    }

    // GET /playlists
    public Collection<Playlist> getPlaylists() {
        return playlistRepository.findAllByAtivoTrue();
    }

    // GET /playlists/{id}
    public Playlist getPlaylist(UUID id) {
        Playlist playlist = playlistRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Essa playlist não existe"));

        if (playlist.getMusicas() == null) {
            playlist.setMusicas(new ArrayList<>());
        }

        return playlist;
    }

    // PUT /playlists/{id}
    public Playlist editPlaylist(UUID id, Playlist dadosAtualizados) {

        Playlist playlist = playlistRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Essa playlist não existe ou não pode ser modificada"));

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

        if (playlistRepository.existsByNomeIgnoreCaseAndIdNot(dadosAtualizados.getNome(), id)) {
            throw new RuntimeException("Já existe uma playlist com esse nome");
        }

        List<Musica> musicasReais = resolverMusicas(dadosAtualizados.getMusicas());

        playlist.setNome(dadosAtualizados.getNome());
        playlist.setUsuario(usuario);
        playlist.setPublica(dadosAtualizados.getPublica());
        playlist.setMusicas(musicasReais);

        return playlistRepository.save(playlist);
    }

    // DELETE /playlists/{id}
    public void deletePlaylist(UUID id) {
        Playlist playlist = playlistRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Essa playlist não existe ou não pode ser modificada"));

        playlist.setAtivo(false);
        playlistRepository.save(playlist);
    }

    // PUT /playlists/reativar/{id}
    public Playlist reactivatePlaylist(UUID id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Essa playlist não existe ou não pode ser modificada"));

        if (playlist.isAtivo()) {
            throw new RuntimeException("Essa playlist já está ativa");
        }

        playlist.setAtivo(true);
        return playlistRepository.save(playlist);
    }

    // POST /playlists/{playlistId}/musicas/{musicaId}
    public Playlist adicionarMusica(UUID pId, UUID mId, UUID usuarioId) {
        Playlist playlist = playlistRepository.findByIdAndAtivoTrue(pId)
                .orElseThrow(() -> new RuntimeException("Essa playlist não existe"));

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
        return playlistRepository.save(playlist);
    }

    public boolean verifyUUID(UUID id) {
        return playlistRepository.existsById(id);
    }

    private List<Musica> resolverMusicas(List<Musica> musicasRecebidas) {
        List<Musica> musicasReais = new ArrayList<>();

        if (musicasRecebidas == null) {
            return musicasReais;
        }

        for (Musica musica : musicasRecebidas) {
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

        return musicasReais;
    }
}