package com.bykeeasy.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir);
        String uploadAbsolutePath = uploadPath.toFile().getAbsolutePath();
        
        // Ajuste para compatibilidad total con rutas de Windows en Spring Boot
        String pathSource = uploadAbsolutePath.replace("\\", "/");
        if (!pathSource.startsWith("/")) {
            pathSource = "/" + pathSource;
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + pathSource + "/");
    }
}
