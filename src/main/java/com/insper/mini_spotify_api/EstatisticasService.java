package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class EstatisticasService {

    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final HashMap<UUID, Estatisticas> estatisticasPorUsuario = new HashMap<>();

    public EstatisticasService(UsuarioService usuarioService, MusicaService musicaService) {
        this.usuarioService = usuarioService;
        this.musicaService = musicaService;
    }

    // POST /usuarios/{usuarioId}/estatisticas
    public Estatisticas criarEstatisticas(UUID usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        if (estatisticasPorUsuario.containsKey(usuarioId)) {
            throw new RuntimeException("Esse usuário já possui estatísticas");
        }

        Estatisticas estatisticas = new Estatisticas();
        estatisticas.setUsuario(usuario);

        estatisticasPorUsuario.put(usuarioId, estatisticas);
        usuario.setEstatisticas(estatisticas);

        return estatisticas;
    }

    // GET /usuarios/{usuarioId}/estatisticas
    public Estatisticas getEstatisticas(UUID usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        Estatisticas estatisticas = estatisticasPorUsuario.get(usuario.getId());

        if (estatisticas == null) {
            throw new RuntimeException("Esse usuário ainda não possui estatísticas");
        }

        return estatisticas;
    }

    // PUT /usuarios/{usuarioId}/estatisticas
    public Estatisticas atualizarEstatisticas(UUID usuarioId, Estatisticas dadosAtualizados) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        Estatisticas estatisticas = estatisticasPorUsuario.get(usuario.getId());

        if (estatisticas == null) {
            throw new RuntimeException("Esse usuário ainda não possui estatísticas");
        }

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        estatisticas.setMusicasReproduzidas(dadosAtualizados.getMusicasReproduzidas());
        estatisticas.setArtistaFavorito(dadosAtualizados.getArtistaFavorito());
        estatisticas.setAlbumFavorito(dadosAtualizados.getAlbumFavorito());
        estatisticas.setMusicaFavorita(dadosAtualizados.getMusicaFavorita());
        estatisticas.setTempoReproducao(dadosAtualizados.getTempoReproducao());

        return estatisticas;
    }

    // DELETE /usuarios/{usuarioId}/estatisticas
    public void deleteEstatisticas(UUID usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        Estatisticas estatisticas = estatisticasPorUsuario.get(usuario.getId());

        if (estatisticas == null) {
            throw new RuntimeException("Esse usuário ainda não possui estatísticas");
        }

        estatisticasPorUsuario.remove(usuario.getId());
        usuario.setEstatisticas(null);
    }

    public Estatisticas registrarReproducao(UUID usuarioId, UUID musicaId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        Musica musica = musicaService.getMusica(musicaId);

        Estatisticas estatisticas = estatisticasPorUsuario.get(usuario.getId());

        if (estatisticas == null) {
            estatisticas = new Estatisticas();
            estatisticas.setUsuario(usuario);
            estatisticasPorUsuario.put(usuario.getId(), estatisticas);
            usuario.setEstatisticas(estatisticas);
        }

        estatisticas.setMusicasReproduzidas(estatisticas.getMusicasReproduzidas() + 1);
        estatisticas.setTempoReproducao(estatisticas.getTempoReproducao() + musica.getDuracaoSegundos());
        estatisticas.setMusicaFavorita(musica);
        estatisticas.setArtistaFavorito(musica.getArtista());
        estatisticas.setAlbumFavorito(musica.getAlbum());

        return estatisticas;
    }
}
