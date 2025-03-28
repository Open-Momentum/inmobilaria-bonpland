package idforideas.bonpland.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Figueroa Mauro
 */
 @Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                       .info(new Info()
                                     .title("Bonpland API")
                                     .version("1.0")
                                     .description("Documentación de la API de Bonpland"))
                       .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                       .components(new io.swagger.v3.oas.models.Components()
                                           .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                                                     .name("bearerAuth")
                                                                                     .type(SecurityScheme.Type.HTTP)
                                                                                     .scheme("bearer")
                                                                                     .bearerFormat("JWT")));
    }

}
