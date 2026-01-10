package br.com.fiap.feedback;

import br.com.fiap.feedback.config.DatabaseConfig;
import br.com.fiap.feedback.dto.AvaliacaoRequestDTO;
import br.com.fiap.feedback.repository.AvaliacaoRepositoryJdbc;
import br.com.fiap.feedback.service.AvaliacaoService;
import br.com.fiap.feedback.util.JsonUtil;
import br.com.fiap.feedback.util.Strings;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Ponto de entrada da Azure Function.
 */
public class FeedbackAzureFunction {

    private static HttpResponseMessage json(HttpRequestMessage<?> request, HttpStatus status, Object body) {
        try {
            String payload = (body instanceof String) ? (String) body : JsonUtil.write(body);
            return request.createResponseBuilder(status)
                    .header("Content-Type", "application/json")
                    .body(payload)
                    .build();
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Falha ao serializar resposta\"}")
                    .build();
        }
    }

    @FunctionName("feedbackApi")
    public HttpResponseMessage create(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                route = "avaliacoes",
                authLevel = AuthorizationLevel.FUNCTION)
            HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        context.getLogger().info("POST " + request.getUri().getPath());

        Optional<String> bodyOpt = request.getBody();
        if (bodyOpt.isEmpty() || Strings.isBlank(bodyOpt.get())) {
            return json(request, HttpStatus.BAD_REQUEST, Map.of("error", "Body JSON é obrigatório"));
        }

        try {
            AvaliacaoRequestDTO dto = JsonUtil.read(bodyOpt.get(), AvaliacaoRequestDTO.class);

            AvaliacaoService service = new AvaliacaoService(
                    new AvaliacaoRepositoryJdbc(DatabaseConfig.fromEnv()));

            Long id = service.criar(dto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Avaliação salva com sucesso");
            if (id != null) {
                response.put("id", id);
            }
            return json(request, HttpStatus.CREATED, response);
        } catch (IllegalArgumentException e) {
            return json(request, HttpStatus.BAD_REQUEST, Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            context.getLogger().severe(e.getMessage());
            return json(request, HttpStatus.INTERNAL_SERVER_ERROR, Map.of("error", "Configuração inválida", "details", e.getMessage()));
        } catch (SQLException e) {
            context.getLogger().severe("Erro ao salvar avaliação no banco: " + e.getMessage());
            return json(request, HttpStatus.INTERNAL_SERVER_ERROR, Map.of("error", "Erro ao salvar avaliação"));
        } catch (Exception e) {
            context.getLogger().severe("Erro ao processar requisição: " + e.getMessage());
            return json(request, HttpStatus.BAD_REQUEST, Map.of("error", "JSON inválido"));
        }
    }
}
