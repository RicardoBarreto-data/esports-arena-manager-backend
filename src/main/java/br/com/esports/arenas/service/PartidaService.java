package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Partida;
import br.com.esports.arenas.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final RankingService rankingService; // Dependência para atualizar o ranking

    // O Spring Boot injeta ambos automaticamente aqui
    public PartidaService(PartidaRepository partidaRepository, RankingService rankingService) {
        this.partidaRepository = partidaRepository;
        this.rankingService = rankingService;
    }

    public List<Partida> listarTodas() {
        return partidaRepository.findAll();
    }

    public Optional<Partida> buscarPorId(Integer id) {
        return partidaRepository.findById(id);
    }

    public Partida salvar(Partida partida) {
        // Garante que toda partida nova comece como AGENDADA
        if (partida.getStatus() == null) {
            partida.setStatus("AGENDADA");
        }
        return partidaRepository.save(partida);
    }

    @Transactional
    public Partida finalizarPartida(Integer id, Integer pA, Integer pB) {
        // 1. Busca a partida no banco
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada com ID: " + id));

        // 2. Atualiza os dados da partida
        partida.setPontuacaoA(pA);
        partida.setPontuacaoB(pB);
        partida.setStatus("CONCLUIDA");

        // 3. Lógica para definir o resultado e atualizar o Ranking
        // Aqui o PartidaService decide o "quem", e o RankingService (com a Calculadora) decide o "quanto"
        if (pA > pB) {
            // Vitória do Time A
            rankingService.registrarResultado(partida.getTorneio(), partida.getTimeA(), "VITORIA");
            rankingService.registrarResultado(partida.getTorneio(), partida.getTimeB(), "DERROTA");
        } else if (pB > pA) {
            // Vitória do Time B
            rankingService.registrarResultado(partida.getTorneio(), partida.getTimeB(), "VITORIA");
            rankingService.registrarResultado(partida.getTorneio(), partida.getTimeA(), "DERROTA");
        } else {
            // Caso de Empate
            rankingService.registrarResultado(partida.getTorneio(), partida.getTimeA(), "EMPATE");
            rankingService.registrarResultado(partida.getTorneio(), partida.getTimeB(), "EMPATE");
        }

        // 4. Salva a partida finalizada
        return partidaRepository.save(partida);
    }

    public void deletar(Integer id) {
        partidaRepository.deleteById(id);
    }
}