package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Jogador;
import br.com.esports.arenas.service.JogadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    // Listar todos
    @GetMapping
    public List<Jogador> listarTodos() {
        return jogadorService.listarTodos();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Jogador buscarPorId(@PathVariable Integer id) {
        return jogadorService.buscarPorId(id);
    }

    // Criar jogador
    @PostMapping
    public Jogador criar(@RequestBody Jogador jogador) {
        return jogadorService.salvar(jogador);
    }

    // Atualizar jogador
    @PutMapping("/{id}")
    public Jogador atualizar(@PathVariable Integer id,
                             @RequestBody Jogador jogador) {
        return jogadorService.atualizar(id, jogador);
    }

    // Deletar jogador
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        jogadorService.deletar(id);
    }
}