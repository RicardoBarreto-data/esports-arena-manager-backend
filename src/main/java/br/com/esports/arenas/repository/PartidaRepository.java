package br.com.esports.arenas.repository;

import br.com.esports.arenas.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
}