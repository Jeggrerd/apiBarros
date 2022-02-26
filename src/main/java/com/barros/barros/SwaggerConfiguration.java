package com.barros.barros;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.barros.barros.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Proyecto Barros")
                .description("Servicios para gestionar eventos y clientes de un restaurante")
                .contact(new Contact("Sergio","","dam17.2021@gmail.com"))
                .version("0.1")
                .build();
    }

}
