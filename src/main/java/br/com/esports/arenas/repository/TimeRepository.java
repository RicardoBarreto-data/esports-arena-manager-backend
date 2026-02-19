package br.com.esports.arenas.repository;

import br.com.esports.arenas.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {
    
    // Este método vai gerar automaticamente a query: 
    // SELECT * FROM time WHERE id_torneio = ?
    List<Time> findByTorneioId(Integer torneioId);
}