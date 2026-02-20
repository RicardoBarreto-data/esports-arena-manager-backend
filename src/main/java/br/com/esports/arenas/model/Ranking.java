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

    // Campos que você já tinha
    private Integer classificacao;
    private Integer pontuacao = 0; // Iniciando com 0

    // NOVOS CAMPOS: Essenciais para o cálculo e para a sua tabela HTML
    private Integer vitorias = 0;
    private Integer derrotas = 0;
    private Integer empates = 0;

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = false) // Remova o referencedColumnName por enquanto
    private Torneio torneio;

    @ManyToOne
    @JoinColumn(name = "time_id", nullable = false) // Remova o referencedColumnName por enquanto
    private Time time;

    // Getters e Setters dos novos campos
    public Integer getVitorias() { return vitorias; }
    public void setVitorias(Integer vitorias) { this.vitorias = vitorias; }

    public Integer getDerrotas() { return derrotas; }
    public void setDerrotas(Integer derrotas) { this.derrotas = derrotas; }

    public Integer getEmpates() { return empates; }
    public void setEmpates(Integer empates) { this.empates = empates; }

    // Getters e Setters originais
    public Integer getId() { return id; }
    public Integer getClassificacao() { return classificacao; }
    public void setClassificacao(Integer classificacao) { this.classificacao = classificacao; }
    public Integer getPontuacao() { return pontuacao; }
    public void setPontuacao(Integer pontuacao) { this.pontuacao = pontuacao; }
    public Torneio getTorneio() { return torneio; }
    public void setTorneio(Torneio torneio) { this.torneio = torneio; }
    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
}