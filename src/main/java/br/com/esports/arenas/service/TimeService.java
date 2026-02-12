package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Time;
import br.com.esports.arenas.repository.TimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    // Listar todos
    public List<Time> listarTodos() {
        return timeRepository.findAll();
    }

    // Buscar por ID
    public Time buscarPorId(Integer id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com id: " + id));
    }

    // Criar novo time
    public Time salvar(Time time) {
        return timeRepository.save(time);
    }

    // Atualizar time existente
    public Time atualizar(Integer id, Time timeAtualizado) {
        Time time = buscarPorId(id);

        time.setNome(timeAtualizado.getNome());
        time.setHistoricoCompeticoes(timeAtualizado.getHistoricoCompeticoes());

        return timeRepository.save(time);
    }

    // Deletar time
    public void deletar(Integer id) {
        Time time = buscarPorId(id);
        timeRepository.delete(time);
    }
}