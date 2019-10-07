package com.cisco.auth.configs;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact CONTACT = new Contact("Ali Moghadam", "", "");

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Coordi Auth Service")
                .description("Coordi Authentication Service is an OAuth 2.0 based security Microservice which is  " +
                        "responsible for handling user and role creation. Among that, it also generates JWT tokens for  " +
                        "communication with Resource Services (other Microservices in the platform). Tokens are short lived " +
                        "and will get regenerated upon request. For any questions please contact support team.")
                .contact(CONTACT)
                .version("1.0")
                .build();
    }

    private Predicate<String> authPaths() {
        return or(regex("/v1/users.*"), regex("/v1/roles"));
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user_action")
                .apiInfo(apiInfo())
                .select()
                .paths(authPaths())
                .build()
                .ignoredParameterTypes(ApiIgnore.class);
    }
}