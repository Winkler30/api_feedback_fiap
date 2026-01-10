package br.com.fiap.feedback.validation;

import br.com.fiap.feedback.dto.AvaliacaoRequestDTO;

import static br.com.fiap.feedback.util.Strings.isBlank;

public final class AvaliacaoValidator {

    private AvaliacaoValidator() {
    }

    public static void validateForCreate(AvaliacaoRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Body JSON é obrigatório");
        }
        if (isBlank(dto.getCodAluno()) || isBlank(dto.getCodAula()) || dto.getNota() == null) {
            throw new IllegalArgumentException("codAluno, codAula e nota são obrigatórios");
        }
    }
}
