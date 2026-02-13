package br.com.esports.arenas.repository;

import br.com.esports.arenas.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {

    // Buscar ranking por torneio
    List<Ranking> findByTorneioId(Integer torneioId);

    // Buscar ranking por time
    List<Ranking> findByTimeId(Integer timeId);
}