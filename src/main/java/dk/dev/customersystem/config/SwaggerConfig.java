package dk.dev.customersystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customersApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer System API")
                        .version("1.0")
                        .description("Customer System API was made with Spring Boot")
                        .contact(new Contact()
                                .name("Amirhossein")
                                .email("amirholyanasabnarab@gmail.com")
                        )
                );
    }
}
