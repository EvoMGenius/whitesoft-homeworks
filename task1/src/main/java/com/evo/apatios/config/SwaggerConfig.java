package com.evo.apatios.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .tags(new Tag("employee-controller", "Employee controller"),
                        new Tag("post-controller", "Post controller"))
                .produces(Sets.newHashSet(APPLICATION_JSON_VALUE))
                .consumes(Sets.newHashSet(APPLICATION_JSON_VALUE));
    }
}
