package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Torneio;
import br.com.esports.arenas.repository.PartidaRepository;
import br.com.esports.arenas.repository.TimeRepository;
import br.com.esports.arenas.repository.TorneioRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
  
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TorneioRepository torneioRepository;
    private final TimeRepository timeRepository;
    private final PartidaRepository partidaRepository;

    public DashboardController(TorneioRepository torneioRepository,
                               TimeRepository timeRepository,
                               PartidaRepository partidaRepository) {
        this.torneioRepository = torneioRepository;
        this.timeRepository = timeRepository;
        this.partidaRepository = partidaRepository;
    }

    @GetMapping("/organizador")
    public Map<String, Object> dashboardOrganizador() {

        Map<String, Object> dados = new HashMap<>();

        dados.put("totalTorneios", torneioRepository.count());
        dados.put("totalTimes", timeRepository.count());
        dados.put("totalPartidas", partidaRepository.count());

        List<Torneio> ultimosTorneios =
                torneioRepository.findTop5ByOrderByDataInicioDesc();

        dados.put("ultimosTorneios", ultimosTorneios);

        return dados;
    }
}

