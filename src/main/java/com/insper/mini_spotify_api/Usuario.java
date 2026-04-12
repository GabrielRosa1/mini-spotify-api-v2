package com.insper.mini_spotify_api;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPlano tipoPlano;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @JsonManagedReference("usuario-estatisticas")
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Estatisticas estatisticas;

    @JsonManagedReference("usuario-playlists")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Playlist> playlists = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String email, TipoPlano tipoPlano) {
        this.nome = nome;
        this.email = email;
        this.tipoPlano = tipoPlano;
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public enum TipoPlano {
        FREE,
        PREMIUM
    }

    @PrePersist
    public void prePersist() {
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoPlano getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(TipoPlano tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Estatisticas getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(Estatisticas estatisticas) {
        this.estatisticas = estatisticas;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}