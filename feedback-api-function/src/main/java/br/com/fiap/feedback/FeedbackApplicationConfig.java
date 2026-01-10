package br.com.fiap.feedback;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.function.Function;

/**
 * Classe de configuração principal do Spring Boot.
 * Define a 'proxyFunction' para garantir que o Spring Cloud Function sempre encontre uma definição.
 */
@SpringBootApplication
@ComponentScan(basePackages = "br.com.fiap.feedback")
@EnableJpaRepositories(basePackages = "br.com.fiap.feedback.repository")
@EntityScan(basePackages = "br.com.fiap.feedback.entity")
public class FeedbackApplicationConfig {

    /**
     * Esta função serve como o ponto de entrada padrão para o Spring Cloud Function.
     * Ela é necessária para evitar o erro "No function defined".
     */
    @Bean
    public Function<Object, Object> proxyFunction() {
        return input -> input;
    }
}
