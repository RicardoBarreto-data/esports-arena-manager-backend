package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Ranking;
import br.com.esports.arenas.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public List<Ranking> listar() {
        return rankingService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranking> buscar(@PathVariable Integer id) {
        return rankingService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/torneio/{torneioId}")
    public List<Ranking> listarPorTorneio(@PathVariable Integer torneioId) {
        return rankingService.listarPorTorneio(torneioId);
    }

    @GetMapping("/time/{timeId}")
    public List<Ranking> listarPorTime(@PathVariable Integer timeId) {
        return rankingService.listarPorTime(timeId);
    }

    @PostMapping
    public Ranking criar(@RequestBody Ranking ranking) {
        return rankingService.salvar(ranking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        rankingService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}