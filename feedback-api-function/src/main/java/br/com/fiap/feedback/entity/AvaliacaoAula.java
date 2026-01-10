package br.com.fiap.feedback.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AVALIACAO_AULA")
public class AvaliacaoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NR_SEQL_AVALIACAO")
    private Long id;

    @Column(name = "COD_ID_AULA")
    private String codAula;

    @Column(name = "NOTA_AVALIACAO")
    private Integer nota;

    @Column(name = "TEXTO_AVALIACAO")
    private String texto;

    @Column(name = "COD_ID_ALUNO")
    private String codAluno;

    @Column(name = "TIMESTAMP_AVALIACAO")
    private LocalDateTime data;

    @PrePersist
    public void prePersist() {
        data = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCodAula() {
        return codAula;
    }

    public void setCodAula(String codAula) {
        this.codAula = codAula;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(String codAluno) {
        this.codAluno = codAluno;
    }

    public LocalDateTime getData() {
        return data;
    }
}
