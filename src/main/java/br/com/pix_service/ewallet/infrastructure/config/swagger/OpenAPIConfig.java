package br.com.pix_service.ewallet.infrastructure.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
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
                        .termsOfService("Terms of service")
                        .contact(new Contact()
                                .name("DEVELOPER IT")
                                .url("https://github.com/Jose-Robert")
                                .email("jrobert.dev@hotmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("License of API")
                                .url("API license URL"))
                        .version("1.0.0"));
    }
}
