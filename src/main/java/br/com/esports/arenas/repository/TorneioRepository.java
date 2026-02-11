package br.com.esports.arenas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.esports.arenas.model.Torneio;

public interface TorneioRepository extends JpaRepository<Torneio, Long> {

}