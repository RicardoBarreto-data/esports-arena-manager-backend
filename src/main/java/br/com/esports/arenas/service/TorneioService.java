package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Torneio;
import br.com.esports.arenas.repository.TorneioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TorneioService {

    private final TorneioRepository torneioRepository;

    public TorneioService(TorneioRepository torneioRepository) {
        this.torneioRepository = torneioRepository;
    }

    public List<Torneio> listarTodos() {
        return torneioRepository.findAll();
    }

    public Optional<Torneio> buscarPorId(Integer id) {
        return torneioRepository.findById(id);
    }

    public Torneio salvar(Torneio torneio) {
        return torneioRepository.save(torneio);
    }

    public void deletar(Integer id) {
        torneioRepository.deleteById(id);
    }

    public Torneio atualizar(Integer id, Torneio novo) {

        Torneio existente = torneioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        existente.setNome(novo.getNome());
        existente.setJogo(novo.getJogo());
        existente.setDescricao(novo.getDescricao());
        existente.setStatus(novo.getStatus());
        existente.setDataInicio(novo.getDataInicio());
        existente.setDataFim(novo.getDataFim());

        return torneioRepository.save(existente);
    }
}