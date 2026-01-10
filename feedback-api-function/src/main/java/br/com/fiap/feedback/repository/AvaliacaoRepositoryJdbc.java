package br.com.fiap.feedback.repository;

import br.com.fiap.feedback.config.DatabaseConfig;
import br.com.fiap.feedback.dto.AvaliacaoRequestDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class AvaliacaoRepositoryJdbc {

    private final DatabaseConfig databaseConfig;

    public AvaliacaoRepositoryJdbc(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Long insert(AvaliacaoRequestDTO dto) throws SQLException {
        String sql = "INSERT INTO AVALIACAO_AULA (COD_ID_AULA, NOTA_AVALIACAO, TEXTO_AVALIACAO, COD_ID_ALUNO, TIMESTAMP_AVALIACAO) "
                + "VALUES (?, ?, ?, ?, SYSDATETIME())";

        try (var connection = databaseConfig.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, dto.getCodAula());
            statement.setInt(2, dto.getNota());
            statement.setString(3, dto.getTexto());
            statement.setString(4, dto.getCodAluno());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys != null && keys.next()) {
                    long id = keys.getLong(1);
                    return keys.wasNull() ? null : id;
                }
            }
        }

        return null;
    }
}
