package br.com.esports.arenas.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "ranking",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"torneio_id", "time_id"})
    }
)
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer classificacao;

    private Integer pontuacao;

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = false)
    private Torneio torneio;

    @ManyToOne
    @JoinColumn(name = "time_id", nullable = false)
    private Time time;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}