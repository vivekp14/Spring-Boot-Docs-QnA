package com.App.Spring.Boot.Docs.QnA.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Document Ingestion & Q&A API")
                        .version("1.0")
                        .description("Spring Boot API for document management and Q&A"));
    }
}
