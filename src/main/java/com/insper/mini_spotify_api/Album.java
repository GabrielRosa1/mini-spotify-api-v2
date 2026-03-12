package com.insper.mini_spotify_api;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public class Album {

    private UUID id;
    private String titulo;
    private LocalDate dataLancamento;
    private Artista artista;


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

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

}
