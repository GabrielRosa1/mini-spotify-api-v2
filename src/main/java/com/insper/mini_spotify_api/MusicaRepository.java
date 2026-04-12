package com.insper.mini_spotify_api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicaRepository extends JpaRepository<Musica, UUID> {
    List<Musica> findAllByAtivoTrue();
    Optional<Musica> findByIdAndAtivoTrue(UUID id);
    List<Musica> findTop10ByAtivoTrueOrderByTotalReproducoesDesc();

    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, UUID id);
}