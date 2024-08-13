package com.fastcampus.toyproject2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

//    @Value("${swagger.base-package}")
//    private String API_BASE_PACKAGE;
//
//    @Value("${swagger.path}")
//    private String API_PATH;
//
    @Value("${swagger.title}")
    private String API_TITLE;

    @Value("${swagger.description}")
    private String API_DESCRIPTION;

    @Value("${swagger.version}")
    private String API_VERSION;


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION));
    }

//    @Bean
//    public GroupedOpenApi api(){
//        String[] paths = {"/product/**","/brand/**"};
//        String[] pacagesToScan = {"com.fastcampus.toyproject2"};
//        return GroupedOpenApi.builder().group("ToyProject2 - 5조")
//                .pathsToMatch(paths)
//                .packagesToScan(pacagesToScan)
//                .build();
//    }
}
