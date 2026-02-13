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

    public void deletar(Integer id) {
        partidaRepository.deleteById(id);
    }
}