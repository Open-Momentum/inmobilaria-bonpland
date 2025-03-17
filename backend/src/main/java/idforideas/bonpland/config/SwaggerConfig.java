package idforideas.bonpland.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Figueroa Mauro
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Documentación API Bonpland", version = "1.0", description = "Swagger UI para la documentación de la aplicación Bonpland")
)
public class SwaggerConfig implements WebMvcConfigurer {

}
