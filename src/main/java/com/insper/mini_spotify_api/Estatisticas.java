package com.insper.mini_spotify_api;

public class Estatisticas {

    private Usuario usuario;
    private Integer musicasReproduzidas;
    private Artista artistaFavorito;
    private Album albumFavorito;
    private Musica musicaFavorita;
    private Integer tempoReproducao;

    public Estatisticas() {
        this.musicasReproduzidas = 0;
        this.tempoReproducao = 0;
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
