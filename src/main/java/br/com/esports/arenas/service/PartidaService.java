package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Partida;
import br.com.esports.arenas.repository.PartidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;

    public PartidaService(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    public List<Partida> listarTodas() {
        return partidaRepository.findAll();
    }

    public Optional<Partida> buscarPorId(Integer id) {
        return partidaRepository.findById(id);
    }

    public Partida salvar(Partida partida) {
        return partidaRepository.save(partida);
    }

    // --- NOVO MÉTODO: A MÁGICA DA FINALIZAÇÃO ---
    public Partida finalizarPartida(Integer id, Integer pA, Integer pB) {
        // 1. Busca a partida (ou lança erro se o ID for inválido)
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada com ID: " + id));

        // 2. Seta as pontuações que vieram do prompt do JS
        partida.setPontuacaoA(pA);
        partida.setPontuacaoB(pB);

        // 3. Muda o status para CONCLUIDA
        partida.setStatus("CONCLUIDA");

        // 4. Salva no banco de dados
        return partidaRepository.save(partida);
    }

    public void deletar(Integer id) {
        partidaRepository.deleteById(id);
    }
}