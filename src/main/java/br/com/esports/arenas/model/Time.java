package br.com.esports.arenas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "time")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_time")
    private Integer id;

    @Column(name = "nome_time", nullable = false, length = 100)
    private String nome;

    @Column(name = "historico_competicoes", columnDefinition = "LONGTEXT", nullable = false)
    private String historicoCompeticoes;

    // Relacionamento com Torneio
    @ManyToOne
    @JoinColumn(name = "id_torneio", nullable = false)
    private Torneio torneio;

    public Time() {}

    // Getters e Setters
    public Torneio getTorneio() { return torneio; }
    public void setTorneio(Torneio torneio) { this.torneio = torneio; }
    
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getHistoricoCompeticoes() { return historicoCompeticoes; }
    public void setHistoricoCompeticoes(String historicoCompeticoes) { this.historicoCompeticoes = historicoCompeticoes; }
}