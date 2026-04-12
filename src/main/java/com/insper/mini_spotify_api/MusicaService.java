package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class MusicaService {

    private final UsuarioService usuarioService;
    private final EstatisticasService estatisticasService;
    private final ArtistaService artistaService;
    private final AlbumService albumService;
    private final MusicaRepository musicaRepository;

    public MusicaService(
            UsuarioService usuarioService,
            EstatisticasService estatisticasService,
            ArtistaService artistaService,
            AlbumService albumService,
            MusicaRepository musicaRepository
    ) {
        this.usuarioService = usuarioService;
        this.estatisticasService = estatisticasService;
        this.artistaService = artistaService;
        this.albumService = albumService;
        this.musicaRepository = musicaRepository;
    }

    // POST /musicas
    public Musica criarMusica(Musica musica) {

        if (musica == null) {
            throw new RuntimeException("Body inválido");
        }

        if (musica.getTitulo() == null || musica.getTitulo().isBlank()) {
            throw new RuntimeException("Título da música é obrigatório");
        }

        if (musica.getArtista() == null || musica.getArtista().getId() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (musica.getAlbum() == null || musica.getAlbum().getId() == null) {
            throw new RuntimeException("Álbum é obrigatório");
        }

        if (!albumService.verifyUUID(musica.getAlbum().getId())) {
            throw new RuntimeException("Id de um álbum válido é obrigatório");
        }

        if (!artistaService.verifyUUID(musica.getArtista().getId())) {
            throw new RuntimeException("Id de um artista válido é obrigatório");
        }

        if (musica.getNumeroFaixa() < 0 || musica.getDuracaoSegundos() < 0) {
            throw new RuntimeException("Campos numeroFaixa e duracaoSegundos devem ser maiores ou iguais a zero");
        }

        if (musicaRepository.existsByTituloIgnoreCase(musica.getTitulo())) {
            throw new RuntimeException("Já existe uma música com esse título");
        }

        musica.setId(null);
        musica.setAlbum(albumService.getAlbum(musica.getAlbum().getId()));
        musica.setArtista(artistaService.getArtista(musica.getArtista().getId()));
        musica.setAtivo(true);
        musica.setTotalReproducoes(0);

        return musicaRepository.save(musica);
    }

    // GET /musicas
    public Collection<Musica> getMusicas() {
        return musicaRepository.findAllByAtivoTrue();
    }

    // GET /musicas/{id}
    public Musica getMusica(UUID id) {
        return musicaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Essa música não existe"));
    }

    // PUT /musicas/{id}
    public Musica editMusica(UUID id, Musica dadosAtualizados) {

        Musica musica = musicaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Essa música não existe ou não pode ser modificada"));

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        if (dadosAtualizados.getTitulo() == null || dadosAtualizados.getTitulo().isBlank()) {
            throw new RuntimeException("Título da música é obrigatório");
        }

        if (dadosAtualizados.getArtista() == null || dadosAtualizados.getArtista().getId() == null) {
            throw new RuntimeException("Artista é obrigatório");
        }

        if (dadosAtualizados.getAlbum() == null || dadosAtualizados.getAlbum().getId() == null) {
            throw new RuntimeException("Álbum é obrigatório");
        }

        if (!albumService.verifyUUID(dadosAtualizados.getAlbum().getId())) {
            throw new RuntimeException("Id de um álbum válido é obrigatório");
        }

        if (!artistaService.verifyUUID(dadosAtualizados.getArtista().getId())) {
            throw new RuntimeException("Id de um artista válido é obrigatório");
        }

        if (dadosAtualizados.getNumeroFaixa() < 0 || dadosAtualizados.getDuracaoSegundos() < 0) {
            throw new RuntimeException("Campos numeroFaixa e duracaoSegundos devem ser maiores ou iguais a zero");
        }

        if (musicaRepository.existsByTituloIgnoreCaseAndIdNot(dadosAtualizados.getTitulo(), id)) {
            throw new RuntimeException("Já existe uma música com esse título");
        }

        musica.setTitulo(dadosAtualizados.getTitulo());
        musica.setAlbum(albumService.getAlbum(dadosAtualizados.getAlbum().getId()));
        musica.setArtista(artistaService.getArtista(dadosAtualizados.getArtista().getId()));
        musica.setNumeroFaixa(dadosAtualizados.getNumeroFaixa());
        musica.setDuracaoSegundos(dadosAtualizados.getDuracaoSegundos());

        return musicaRepository.save(musica);
    }

    // DELETE /musicas/{id}
    public void deleteMusica(UUID id) {
        Musica musica = musicaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Essa música não existe ou não pode ser modificada"));

        musica.setAtivo(false);
        musicaRepository.save(musica);
    }

    // PUT /musicas/reativar/{id}
    public Musica reactivateMusica(UUID id) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Essa música não existe ou não pode ser modificada"));

        if (musica.isAtivo()) {
            throw new RuntimeException("Essa música já está ativa");
        }

        musica.setAtivo(true);
        return musicaRepository.save(musica);
    }

    // POST /musicas/{id}/reproduzir
    public Musica reproduzirMusica(UUID musicaId, UUID usuarioId) {
        Musica musica = getMusica(musicaId);
        Usuario usuario = usuarioService.getUsuario(usuarioId);

        if (!usuario.isAtivo()) {
            throw new RuntimeException("Usuário inativo não pode reproduzir música");
        }

        musica.setTotalReproducoes(musica.getTotalReproducoes() + 1);
        Musica musicaAtualizada = musicaRepository.save(musica);

        estatisticasService.registrarReproducao(usuarioId, musicaAtualizada);

        return musicaAtualizada;
    }

    // GET /musicas/top-musicas
    public Collection<Musica> getTop10Musicas() {
        return musicaRepository.findTop10ByAtivoTrueOrderByTotalReproducoesDesc();
    }

}