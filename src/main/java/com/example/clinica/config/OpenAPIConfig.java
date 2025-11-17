package com.example.clinica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI clinicApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clínica Veterinária - API")
                        .description("API da Clínica Veterinária — documentação completa dos endpoints 🐾")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Suporte da Clínica")
                                .email("contato@clinicavet.com")
                                .url("https://clinicavet.com")
                        )
                        .license(new License()
                                .name("Licença - Uso interno")
                                .url("https://clinicavet.com/licenca")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação Complementar da Clínica Veterinária")
                        .url("https://clinicavet.com/docs")
                );
    }
}
