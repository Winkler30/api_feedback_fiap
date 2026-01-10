package br.com.fiap.feedback.service;

import br.com.fiap.feedback.dto.AvaliacaoRequestDTO;
import br.com.fiap.feedback.repository.AvaliacaoRepositoryJdbc;
import br.com.fiap.feedback.validation.AvaliacaoValidator;

import java.sql.SQLException;

public final class AvaliacaoService {

    private final AvaliacaoRepositoryJdbc repository;

    public AvaliacaoService(AvaliacaoRepositoryJdbc repository) {
        this.repository = repository;
    }

    public Long criar(AvaliacaoRequestDTO dto) throws SQLException {
        AvaliacaoValidator.validateForCreate(dto);
        return repository.insert(dto);
    }
}
