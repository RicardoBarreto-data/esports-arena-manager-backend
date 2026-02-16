package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Torneio;
import br.com.esports.arenas.service.TorneioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torneios")
public class TorneioController {

    private final TorneioService torneioService;

    public TorneioController(TorneioService torneioService) {
        this.torneioService = torneioService;
    }

    @GetMapping
    public List<Torneio> listar() {
        return torneioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneio> buscar(@PathVariable Integer id) {
        return torneioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Torneio criar(@RequestBody Torneio torneio) {
        return torneioService.salvar(torneio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneio> atualizar(@PathVariable Integer id,
                                             @RequestBody Torneio torneio) {

        return torneioService.buscarPorId(id)
                .map(t -> {
                    torneio.setId(id);
                    return ResponseEntity.ok(torneioService.salvar(torneio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        torneioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}