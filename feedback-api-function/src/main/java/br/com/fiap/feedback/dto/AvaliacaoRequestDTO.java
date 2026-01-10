package br.com.fiap.feedback.dto;

public class AvaliacaoRequestDTO {

    private String codAluno;
    private String codAula;
    private Integer nota;
    private String texto;

    public String getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(String codAluno) {
        this.codAluno = codAluno;
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
}
