package com.insper.mini_spotify_api;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private int duracaoSegundos;

    @Column(nullable = false)
    private int numeroFaixa;

    @JsonBackReference("album-musicas")
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @JsonIgnoreProperties({"albuns"})
    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    @Column(nullable = false)
    private long totalReproducoes = 0;

    @Column(nullable = false)
    private boolean ativo = true;

    public Musica() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    public int getNumeroFaixa() {
        return numeroFaixa;
    }

    public void setNumeroFaixa(int numeroFaixa) {
        this.numeroFaixa = numeroFaixa;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public long getTotalReproducoes() {
        return totalReproducoes;
    }

    public void setTotalReproducoes(long totalReproducoes) {
        this.totalReproducoes = totalReproducoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}