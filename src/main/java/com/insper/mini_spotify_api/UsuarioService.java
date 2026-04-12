package com.insper.mini_spotify_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private void prepararRelacionamentos(Usuario usuario) {
        if (usuario.getEstatisticas() != null) {
            usuario.getEstatisticas().setUsuario(usuario);

            if (usuario.getEstatisticas().getMusicasReproduzidas() == null) {
                usuario.getEstatisticas().setMusicasReproduzidas(0);
            }

            if (usuario.getEstatisticas().getTempoReproducao() == null) {
                usuario.getEstatisticas().setTempoReproducao(0);
            }
        }

        if (usuario.getPlaylists() == null) {
            usuario.setPlaylists(new ArrayList<>());
        }

        for (Playlist playlist : usuario.getPlaylists()) {
            playlist.setUsuario(usuario);

            if (playlist.getDataCriacao() == null) {
                playlist.setDataCriacao(LocalDateTime.now());
            }

            if (playlist.getPublica() == null) {
                playlist.setPublica(false);
            }

            if (playlist.getMusicas() == null) {
                playlist.setMusicas(new ArrayList<>());
            }

            playlist.setAtivo(true);
        }
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new RuntimeException("Body inválido");
        }

        if (usuario.getNome() == null || usuario.getNome().isBlank()
                || usuario.getEmail() == null || usuario.getEmail().isBlank()
                || usuario.getTipoPlano() == null) {
            throw new RuntimeException("Dados inválidos");
        }

        if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new RuntimeException("Um usuário com esse e-mail já existe");
        }

        usuario.setId(null);
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        prepararRelacionamentos(usuario);

        return usuarioRepository.save(usuario);
    }

    public Collection<Usuario> getUsuarios() {
        return usuarioRepository.findAllByAtivoTrue();
    }

    public Usuario getUsuario(UUID id) {
        return usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse usuário não existe ou está inativo"));
    }

    public Usuario editUsuario(UUID id, Usuario dadosAtualizados) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse usuário não existe ou não pode ser modificado"));

        if (dadosAtualizados == null) {
            throw new RuntimeException("Body inválido");
        }

        if (dadosAtualizados.getNome() == null || dadosAtualizados.getNome().isBlank()
                || dadosAtualizados.getEmail() == null || dadosAtualizados.getEmail().isBlank()
                || dadosAtualizados.getTipoPlano() == null) {
            throw new RuntimeException("Dados inválidos");
        }

        if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dadosAtualizados.getEmail(), id)) {
            throw new RuntimeException("Um usuário com esse e-mail já existe");
        }

        usuario.setNome(dadosAtualizados.getNome());
        usuario.setEmail(dadosAtualizados.getEmail());
        usuario.setTipoPlano(dadosAtualizados.getTipoPlano());

        if (dadosAtualizados.getEstatisticas() != null) {
            usuario.setEstatisticas(dadosAtualizados.getEstatisticas());
        }

        if (dadosAtualizados.getPlaylists() != null) {
            usuario.setPlaylists(dadosAtualizados.getPlaylists());
        }

        prepararRelacionamentos(usuario);

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Esse usuário não existe ou não pode ser modificado"));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    public Usuario reactivateUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esse usuário não existe ou não pode ser modificado"));

        if (usuario.isAtivo()) {
            throw new RuntimeException("Esse usuário já está ativo");
        }

        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }

    public boolean verifyUUID(UUID id) {
        return usuarioRepository.existsById(id);
    }
}