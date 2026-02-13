package br.com.esports.arenas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime data;

    private String status;

    @ManyToOne
    @JoinColumn(name = "time_casa_id", nullable = false)
    private Time timeCasa;

    @ManyToOne
    @JoinColumn(name = "time_visitante_id", nullable = false)
    private Time timeVisitante;

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = false)
    private Torneio torneio;

    public Partida() {}

    public Integer getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(Time timeCasa) {
        this.timeCasa = timeCasa;
    }

    public Time getTimeVisitante() {
        return timeVisitante;
    }

    public void setTimeVisitante(Time timeVisitante) {
        this.timeVisitante = timeVisitante;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }
}