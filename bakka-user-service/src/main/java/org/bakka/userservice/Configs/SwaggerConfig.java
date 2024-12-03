package org.bakka.userservice.Configs;

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
                        .title("La documentation du backend de Bakka")
                        .version("1.0.0")
                        .description("Ceci est une documentation pour la documentation du backend de Bakka"));
    }
}
