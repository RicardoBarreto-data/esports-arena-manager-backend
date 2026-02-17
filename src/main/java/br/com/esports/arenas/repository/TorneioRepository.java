package br.com.esports.arenas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.esports.arenas.model.Torneio;
import java.util.List;

public interface TorneioRepository extends JpaRepository<Torneio, Integer> {

    public List<Torneio> findTop5ByOrderByDataInicioDesc();

}