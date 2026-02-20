package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Ranking;
import br.com.esports.arenas.model.Torneio;
import br.com.esports.arenas.model.Time;
import br.com.esports.arenas.repository.RankingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    private final CalculadoraPontuacao calculadora; // Injeção da sua classe da Etapa 9

    public RankingService(RankingRepository rankingRepository, CalculadoraPontuacao calculadora) {
        this.rankingRepository = rankingRepository;
        this.calculadora = calculadora;
    }

    // --- MÉTODOS EXISTENTES ---
    public List<Ranking> listarTodos() { return rankingRepository.findAll(); }
    public Optional<Ranking> buscarPorId(Integer id) { return rankingRepository.findById(id); }
    public List<Ranking> listarPorTorneio(Integer torneioId) { return rankingRepository.findByTorneioId(torneioId); }
    public List<Ranking> listarPorTime(Integer timeId) { return rankingRepository.findByTimeId(timeId); }
    public Ranking salvar(Ranking ranking) { return rankingRepository.save(ranking); }
    public void deletar(Integer id) { rankingRepository.deleteById(id); }

    // --- LÓGICA DE ATUALIZAÇÃO AUTOMÁTICA (ETAPA 9) ---
    @Transactional
    public void registrarResultado(Torneio torneio, Time time, String tipoResultado) {
        // 1. Tenta achar o ranking desse time no torneio, se não existir, cria um novo
        Ranking ranking = rankingRepository.findByTorneioIdAndTimeId(torneio.getId(), time.getId())
                .orElseGet(() -> {
                    Ranking novo = new Ranking();
                    novo.setTorneio(torneio);
                    novo.setTime(time);
                    return novo;
                });

        // 2. Atualiza os contadores baseado no resultado da partida
        if ("VITORIA".equals(tipoResultado)) {
            ranking.setVitorias(ranking.getVitorias() + 1);
        } else if ("DERROTA".equals(tipoResultado)) {
            ranking.setDerrotas(ranking.getDerrotas() + 1);
        } else if ("EMPATE".equals(tipoResultado)) {
            ranking.setEmpates(ranking.getEmpates() + 1);
        }

        // 3. USA A CALCULADORA (A responsabilidade de saber o peso dos pontos é dela!)
        int novaPontuacao = calculadora.calcularPontuacao(
                ranking.getVitorias(), 
                ranking.getEmpates(), 
                ranking.getDerrotas()
        );

        ranking.setPontuacao(novaPontuacao);

        // 4. Salva o ranking atualizado
        rankingRepository.save(ranking);
        
        // 5. Opcional: Recalcular a classificação (quem é 1º, 2º, etc)
        atualizarPosicoes(torneio.getId());
    }

    private void atualizarPosicoes(Integer torneioId) {
        List<Ranking> rankings = rankingRepository.findByTorneioIdOrderByPontuacaoDesc(torneioId);
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setClassificacao(i + 1);
            rankingRepository.save(rankings.get(i));
        }
    }
}