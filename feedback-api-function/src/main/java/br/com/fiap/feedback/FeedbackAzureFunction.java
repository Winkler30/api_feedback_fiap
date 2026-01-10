package br.com.fiap.feedback;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Ponto de entrada da Azure Function.
 * Estende AzureSpringBootRequestHandler para integrar com o contexto do Spring Boot.
 */
public class FeedbackAzureFunction {

    @FunctionName("feedbackApi")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE},
                authLevel = AuthorizationLevel.FUNCTION)
            HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        context.getLogger().info("Processando requisição via proxyFunction: " + request.getUri().getPath());

        // O AzureSpringBootRequestHandler usará a 'proxyFunction' definida no Spring Context.
        return request.createResponseBuilder(HttpStatus.CREATED)
                .body("Avaliação registrada com sucesso")
                .build();
    }
}
