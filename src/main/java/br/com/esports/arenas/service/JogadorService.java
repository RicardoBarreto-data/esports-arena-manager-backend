package br.com.esports.arenas.service;

import br.com.esports.arenas.model.Jogador;
import br.com.esports.arenas.repository.JogadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    public JogadorService(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    // Listar todos
    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    // Buscar por ID
    public Jogador buscarPorId(Integer id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado com id: " + id));
    }

    // Criar jogador
    public Jogador salvar(Jogador jogador) {
        return jogadorRepository.save(jogador);
    }

    // Atualizar jogador
    public Jogador atualizar(Integer id, Jogador jogadorAtualizado) {
        Jogador jogador = buscarPorId(id);

        jogador.setNome(jogadorAtualizado.getNome());
        jogador.setPerfil(jogadorAtualizado.getPerfil());
        jogador.setDadosContato(jogadorAtualizado.getDadosContato());
        jogador.setTime(jogadorAtualizado.getTime());

        return jogadorRepository.save(jogador);
    }

    // Deletar jogador
    public void deletar(Integer id) {
        Jogador jogador = buscarPorId(id);
        jogadorRepository.delete(jogador);
    }
}