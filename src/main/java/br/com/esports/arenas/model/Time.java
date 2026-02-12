package br.com.esports.arenas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

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

    public Time() {}

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHistoricoCompeticoes() {
        return historicoCompeticoes;
    }

    public void setHistoricoCompeticoes(String historicoCompeticoes) {
        this.historicoCompeticoes = historicoCompeticoes;
    }
}