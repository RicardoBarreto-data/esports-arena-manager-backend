package br.com.esports.arenas.controller;

import br.com.esports.arenas.model.Jogador;
import br.com.esports.arenas.model.Torneio;
import br.com.esports.arenas.repository.JogadorRepository;
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
    private final JogadorRepository JogadorRepository;

    public DashboardController(TorneioRepository torneioRepository,
                           TimeRepository timeRepository,
                           PartidaRepository partidaRepository,
                           JogadorRepository jogadorRepository) {
    this.torneioRepository = torneioRepository;
    this.timeRepository = timeRepository;
    this.partidaRepository = partidaRepository;
    this.JogadorRepository = jogadorRepository;
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
    @GetMapping("/admin")
    public Map<String, Object> dashboardAdmin() {

    Map<String, Object> dados = new HashMap<>();

    dados.put("totalTorneios", torneioRepository.count());
    dados.put("totalTimes", timeRepository.count());
    dados.put("totalJogadores", JogadorRepository.count());

            List<Jogador> ultimosJogadores =
            JogadorRepository.findTop5ByOrderByIdDesc();

    dados.put("ultimosJogadores", ultimosJogadores);

    return dados;
}
}

