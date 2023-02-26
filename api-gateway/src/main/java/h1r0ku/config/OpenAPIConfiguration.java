package h1r0ku.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI gateWayOpenApi() {
        return new OpenAPI().info(new Info().title("Demo Application Microservices APIs ")
                .description("Documentation for all the Microservices in Demo Application")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("Demo Application Development Team")
                        .email("demo_support@imaginarycompany.com")));
    }
}