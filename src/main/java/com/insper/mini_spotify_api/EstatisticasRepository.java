package com.insper.mini_spotify_api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstatisticasRepository extends JpaRepository<Estatisticas, UUID> {
    Optional<Estatisticas> findByUsuarioId(UUID usuarioId);
}