package br.com.pix_service.ewallet.infrastructure.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet API - Pix Service")
                        .description("Microserviço de carteira com suporte a Pix, garantindo consistência sob concorrência e idempotência.")
                        .version("1.0.0"));
    }
}
