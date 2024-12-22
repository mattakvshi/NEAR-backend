package ru.mattakvshi.near.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mattakvshi.near.SystemConstants;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI nearAPI() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("https://localhost:8080")
                        )
                )
                .info(
                        new Info().title("Near API")
                );
    }

}
