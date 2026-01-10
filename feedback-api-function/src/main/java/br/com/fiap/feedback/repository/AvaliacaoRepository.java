package br.com.fiap.feedback.repository;

import br.com.fiap.feedback.entity.AvaliacaoAula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoAula, Long> {
}
