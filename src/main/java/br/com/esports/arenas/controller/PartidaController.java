package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Partida;
import br.com.esports.arenas.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Não esqueça desse import!

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping
    public List<Partida> listarTodas() {
        return partidaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPorId(@PathVariable Integer id) {
        return partidaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Partida criar(@RequestBody Partida partida) {
        // Garante o status inicial
        if (partida.getStatus() == null) partida.setStatus("AGENDADA");
        return partidaService.salvar(partida);
    }

    // --- O MÉTODO QUE ESTAVA FALTANDO ABAIXO ---

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizar(@PathVariable Integer id, @RequestBody Map<String, Integer> scores) {
        try {
            Integer pA = scores.get("pontuacaoA");
            Integer pB = scores.get("pontuacaoB");
            
            Partida partidaFinalizada = partidaService.finalizarPartida(id, pA, pB);
            return ResponseEntity.ok(partidaFinalizada);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        partidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}