package com.ml.comcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author elizcano
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Configuración de paquete com.ml.comcenter para api docs
     * de swagger
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ml.comcenter"))
                .paths(PathSelectors.any())
                .build();
    }

}
