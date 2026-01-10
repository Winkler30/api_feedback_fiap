package br.com.fiap.feedback.controller;

import br.com.fiap.feedback.dto.AvaliacaoRequestDTO;
import br.com.fiap.feedback.entity.AvaliacaoAula;
import br.com.fiap.feedback.repository.AvaliacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoRepository repository;

    public AvaliacaoController(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AvaliacaoRequestDTO dto) {

        if (dto.getCodAluno() == null || dto.getCodAula() == null || dto.getNota() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("codAluno, codAula e nota são obrigatórios");
        }

        AvaliacaoAula avaliacao = new AvaliacaoAula();
        avaliacao.setCodAluno(dto.getCodAluno());
        avaliacao.setCodAula(dto.getCodAula());
        avaliacao.setNota(dto.getNota());
        avaliacao.setTexto(dto.getTexto());

        repository.save(avaliacao);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Avaliação salva com sucesso");
    }
}
