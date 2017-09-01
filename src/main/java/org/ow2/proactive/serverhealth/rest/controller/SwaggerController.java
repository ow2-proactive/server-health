package org.ow2.proactive.schedulerhealth.rest.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
/**
 * Created by Gaëtan Hurel on 9/1/17.
 *
 * Simple controller for Swagger and Swagger UI.
 */
public class SwaggerController {
    @Bean

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("ProActive Server Health Service API")
                .description("The purpose of this service is to get information about  " +
                        "the internal state of the ProActive server (scheduler, rm, studios and so on.")
                .version("1.0")
                .build();
    }
}
