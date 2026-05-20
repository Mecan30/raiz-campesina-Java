
package com.raizcampesina.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer
    {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // Vincula de forma absoluta tu carpeta física de desarrollo para que Spring vea los archivos sin reiniciar
            String rutaProductos = Paths.get("src/main/resources/static/images/productos").toAbsolutePath().toUri().toString();

            registry.addResourceHandler("/images/productos/**")
                    .addResourceLocations(rutaProductos);
        }
    }

