package net.xeill.elpuig.apipatitasconectadas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta clase es una clase de configuración de Spring
public class CorsConfig {

    // Define un bean que proporciona configuración para CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            // Sobrescribe el método addCorsMappings para configurar los permisos de CORS
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite solicitudes CORS en todas las rutas (/**)
                        .allowedOrigins("*") // Permite todas las fuentes (orígenes) para acceder a los recursos
                        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS") // Permite estos métodos HTTP para solicitudes CORS
                        .allowedHeaders("*"); // Permite todos los encabezados en las solicitudes CORS
            }
        };
    }
}
