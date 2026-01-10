package br.com.fiap.feedback.infrastructure;

import br.com.fiap.feedback.config.DatabaseConfig;
import br.com.fiap.feedback.repository.AvaliacaoRepositoryJdbc;
import br.com.fiap.feedback.service.AvaliacaoService;

import javax.sql.DataSource;

public final class AppContext {

    private static final DataSource DATA_SOURCE = DatabaseConfig.dataSource();
    private static final AvaliacaoRepositoryJdbc AVALIACAO_REPOSITORY = new AvaliacaoRepositoryJdbc(DATA_SOURCE);
    private static final AvaliacaoService AVALIACAO_SERVICE = new AvaliacaoService(AVALIACAO_REPOSITORY);

    private AppContext() {}

    public static AvaliacaoService avaliacaoService() {
        return AVALIACAO_SERVICE;
    }

    public static DataSource dataSource() {
        return DATA_SOURCE;
    }
}
