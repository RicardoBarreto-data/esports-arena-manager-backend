package br.com.esports.arenas.repository;

import br.com.esports.arenas.model.Jogador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    List<Jogador> findTop5ByOrderByIdDesc();

}