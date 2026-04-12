package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EstatisticasService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final EstatisticasRepository estatisticasRepository;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private final MusicaRepository musicaRepository;

    public EstatisticasService(
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            EstatisticasRepository estatisticasRepository,
            ArtistaRepository artistaRepository,
            AlbumRepository albumRepository,
            MusicaRepository musicaRepository
    ) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.estatisticasRepository = estatisticasRepository;
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
        this.musicaRepository = musicaRepository;
    }

    public Estatisticas criarEstatisticas(UUID usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        if (estatisticasRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Esse usuário já possui estatísticas");
        }

        Estatisticas estatisticas = new Estatisticas();
        estatisticas.setUsuario(usuario);

        estatisticas = estatisticasRepository.save(estatisticas);

        usuario.setEstatisticas(estatisticas);
        usuarioRepository.save(usuario);

        return estatisticas;
    }

    public Estatisticas getEstatisticas(UUID usuarioId) {
        usuarioService.getUsuario(usuarioId);

        return estatisticasRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Esse usuário ainda não possui estatísticas"));
    }

    public Estatisticas atualizarEstatisticas(UUID usuarioId, Estatisticas dadosAtualizados) {
        usuarioService.getUsuario(usuarioId);

        Estatisticas estatisticas = estatisticasRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Esse usuário ainda não possui estatísticas"));

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        estatisticas.setMusicasReproduzidas(
                dadosAtualizados.getMusicasReproduzidas() != null ? dadosAtualizados.getMusicasReproduzidas() : 0
        );

        estatisticas.setTempoReproducao(
                dadosAtualizados.getTempoReproducao() != null ? dadosAtualizados.getTempoReproducao() : 0
        );

        if (dadosAtualizados.getArtistaFavorito() != null && dadosAtualizados.getArtistaFavorito().getId() != null) {
            Artista artista = artistaRepository.findByIdAndAtivoTrue(dadosAtualizados.getArtistaFavorito().getId())
                    .orElseThrow(() -> new RuntimeException("Artista favorito inválido"));
            estatisticas.setArtistaFavorito(artista);
        } else {
            estatisticas.setArtistaFavorito(null);
        }

        if (dadosAtualizados.getAlbumFavorito() != null && dadosAtualizados.getAlbumFavorito().getId() != null) {
            Album album = albumRepository.findByIdAndAtivoTrue(dadosAtualizados.getAlbumFavorito().getId())
                    .orElseThrow(() -> new RuntimeException("Álbum favorito inválido"));
            estatisticas.setAlbumFavorito(album);
        } else {
            estatisticas.setAlbumFavorito(null);
        }

        if (dadosAtualizados.getMusicaFavorita() != null && dadosAtualizados.getMusicaFavorita().getId() != null) {
            Musica musica = musicaRepository.findByIdAndAtivoTrue(dadosAtualizados.getMusicaFavorita().getId())
                    .orElseThrow(() -> new RuntimeException("Música favorita inválida"));
            estatisticas.setMusicaFavorita(musica);
        } else {
            estatisticas.setMusicaFavorita(null);
        }

        return estatisticasRepository.save(estatisticas);
    }

    public void deleteEstatisticas(UUID usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        Estatisticas estatisticas = estatisticasRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Esse usuário ainda não possui estatísticas"));

        usuario.setEstatisticas(null);
        usuarioRepository.save(usuario);

        estatisticasRepository.delete(estatisticas);
    }

    public Estatisticas registrarReproducao(UUID usuarioId, Musica musica) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        Estatisticas estatisticas = estatisticasRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Estatisticas nova = new Estatisticas();
                    nova.setUsuario(usuario);
                    Estatisticas salva = estatisticasRepository.save(nova);
                    usuario.setEstatisticas(salva);
                    usuarioRepository.save(usuario);
                    return salva;
                });

        estatisticas.setMusicasReproduzidas(
                (estatisticas.getMusicasReproduzidas() == null ? 0 : estatisticas.getMusicasReproduzidas()) + 1
        );

        estatisticas.setTempoReproducao(
                (estatisticas.getTempoReproducao() == null ? 0 : estatisticas.getTempoReproducao()) + musica.getDuracaoSegundos()
        );

        estatisticas.setMusicaFavorita(musica);
        estatisticas.setArtistaFavorito(musica.getArtista());
        estatisticas.setAlbumFavorito(musica.getAlbum());

        return estatisticasRepository.save(estatisticas);
    }
}