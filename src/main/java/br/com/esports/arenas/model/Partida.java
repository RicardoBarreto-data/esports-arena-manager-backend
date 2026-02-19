package br.com.esports.arenas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partida")
public class Partida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dataHora;
    private String status; // "AGENDADA", "EM_ANDAMENTO", "CONCLUIDA"
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Integer pontuacaoA; 
    private Integer pontuacaoB; 

    @ManyToOne
    @JoinColumn(name = "id_torneio", nullable = false)
    private Torneio torneio;

    @ManyToOne
    @JoinColumn(name = "id_time_a", nullable = false)
    private Time timeA;

    @ManyToOne
    @JoinColumn(name = "id_time_b", nullable = false)
    private Time timeB;

    public Partida() {}

    // --- Getters e Setters Padronizados ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getPontuacaoA() { return pontuacaoA; }
    public void setPontuacaoA(Integer pontuacaoA) { this.pontuacaoA = pontuacaoA; }

    public Integer getPontuacaoB() { return pontuacaoB; }
    public void setPontuacaoB(Integer pontuacaoB) { this.pontuacaoB = pontuacaoB; }

    public Torneio getTorneio() { return torneio; }
    public void setTorneio(Torneio torneio) { this.torneio = torneio; }

    public Time getTimeA() { return timeA; }
    public void setTimeA(Time timeA) { this.timeA = timeA; }

    public Time getTimeB() { return timeB; }
    public void setTimeB(Time timeB) { this.timeB = timeB; }
}