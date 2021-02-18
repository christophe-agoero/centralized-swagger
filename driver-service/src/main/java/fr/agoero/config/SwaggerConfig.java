package fr.agoero.config;


import static fr.agoero.util.ConstantsAPI.API_VERSION;

import fr.agoero.config.properties.GatewayProperties;
import fr.agoero.config.properties.SwaggerProperties;
import java.util.Set;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;
    private final GatewayProperties gatewayProperties;

    /**
     * Base object of swagger configuration
     *
     * @return Docket
     */
    @Bean
    public Docket docket() {
        Predicate<RequestHandler> selector = RequestHandlerSelectors.basePackage("fr.agoero.controller");
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .protocols(
                Set.of(gatewayProperties.getProtocol()))
            .select()
            .apis(selector)
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * Info UI
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .version(API_VERSION)
            .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
            .contact(
                new Contact(
                    swaggerProperties.getContactName(),
                    swaggerProperties.getContactUrl(),
                    swaggerProperties.getContactEmail()
                ))
            .license(swaggerProperties.getLicense())
            .licenseUrl(swaggerProperties.getLicenseUrl())
            .build();
    }

}
