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
}