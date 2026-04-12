package com.insper.mini_spotify_api;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "estatisticas")
public class Estatisticas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonBackReference("usuario-estatisticas")
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private Integer musicasReproduzidas = 0;

    @ManyToOne
    @JoinColumn(name = "artista_favorito_id")
    private Artista artistaFavorito;

    @ManyToOne
    @JoinColumn(name = "album_favorito_id")
    private Album albumFavorito;

    @ManyToOne
    @JoinColumn(name = "musica_favorita_id")
    private Musica musicaFavorita;

    @Column(nullable = false)
    private Integer tempoReproducao = 0;

    public Estatisticas() {
        this.musicasReproduzidas = 0;
        this.tempoReproducao = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getMusicasReproduzidas() {
        return musicasReproduzidas;
    }

    public void setMusicasReproduzidas(Integer musicasReproduzidas) {
        this.musicasReproduzidas = musicasReproduzidas;
    }

    public Artista getArtistaFavorito() {
        return artistaFavorito;
    }

    public void setArtistaFavorito(Artista artistaFavorito) {
        this.artistaFavorito = artistaFavorito;
    }

    public Album getAlbumFavorito() {
        return albumFavorito;
    }

    public void setAlbumFavorito(Album albumFavorito) {
        this.albumFavorito = albumFavorito;
    }

    public Musica getMusicaFavorita() {
        return musicaFavorita;
    }

    public void setMusicaFavorita(Musica musicaFavorita) {
        this.musicaFavorita = musicaFavorita;
    }

    public Integer getTempoReproducao() {
        return tempoReproducao;
    }

    public void setTempoReproducao(Integer tempoReproducao) {
        this.tempoReproducao = tempoReproducao;
    }
}