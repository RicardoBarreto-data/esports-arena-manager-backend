package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Time;
import br.com.esports.arenas.service.TimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    // Listar todos
    @GetMapping
    public List<Time> listarTodos() {
        return timeService.listarTodos();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Time buscarPorId(@PathVariable Integer id) {
        return timeService.buscarPorId(id);
    }

    // Criar novo time
    @PostMapping
    public Time criar(@RequestBody Time time) {
        return timeService.salvar(time);
    }

    // Atualizar time
    @PutMapping("/{id}")
    public Time atualizar(@PathVariable Integer id, @RequestBody Time time) {
        return timeService.atualizar(id, time);
    }

    // Deletar time
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        timeService.deletar(id);
    }
}