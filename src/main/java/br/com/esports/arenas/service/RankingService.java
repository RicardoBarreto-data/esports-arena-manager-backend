package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Ranking;
import br.com.esports.arenas.repository.RankingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public List<Ranking> listarTodos() {
        return rankingRepository.findAll();
    }

    public Optional<Ranking> buscarPorId(Integer id) {
        return rankingRepository.findById(id);
    }

    public List<Ranking> listarPorTorneio(Integer torneioId) {
        return rankingRepository.findByTorneioId(torneioId);
    }

    public List<Ranking> listarPorTime(Integer timeId) {
        return rankingRepository.findByTimeId(timeId);
    }

    public Ranking salvar(Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    public void deletar(Integer id) {
        rankingRepository.deleteById(id);
    }
}