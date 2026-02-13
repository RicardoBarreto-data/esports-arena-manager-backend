package br.com.esports.arenas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jogador")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogador")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String perfil;

    @Column(name = "dados_contato", nullable = false, length = 150)
    private String dadosContato;

    @ManyToOne
    @JoinColumn(name = "id_time", nullable = false)
    private Time time;

    public Jogador() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDadosContato() {
        return dadosContato;
    }

    public void setDadosContato(String dadosContato) {
        this.dadosContato = dadosContato;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}