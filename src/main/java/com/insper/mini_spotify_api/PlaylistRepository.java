package com.insper.mini_spotify_api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {

    List<Playlist> findAllByAtivoTrue();

    Optional<Playlist> findByIdAndAtivoTrue(UUID id);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);
}