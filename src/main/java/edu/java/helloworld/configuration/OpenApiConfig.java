package edu.java.helloworld.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;



@Configuration
// @Profile({"test","dev"})
@Profile("!prod")
public class OpenApiConfig {
//thay doi Ten api trong swagger
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("API-services").version("v1.2.0"));
    }
}
