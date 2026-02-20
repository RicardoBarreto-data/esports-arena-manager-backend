package br.com.esports.arenas.repository;

import br.com.esports.arenas.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {

    // Adicionamos o "OrderByPontuacaoDesc" para o 1º lugar vir sempre primeiro
    List<Ranking> findByTorneioIdOrderByPontuacaoDesc(Integer torneioId);

    List<Ranking> findByTimeId(Integer timeId);

    // ESSENCIAL: Para encontrar o ranking de um time específico em um torneio e atualizá-lo
    Optional<Ranking> findByTorneioIdAndTimeId(Integer torneioId, Integer timeId);

    public List<Ranking> findByTorneioId(Integer torneioId);

}