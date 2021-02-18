package fr.agoero.config;


import static fr.agoero.util.ConstantsAPI.API_VERSION;

import com.fasterxml.classmate.TypeResolver;
import fr.agoero.config.properties.GatewayProperties;
import fr.agoero.config.properties.SwaggerProperties;
import fr.agoero.exception.ApiErrorDTO;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;
    private final GatewayProperties gatewayProperties;
    private final TypeResolver typeResolver;

    /**
     * Base object of swagger configuration
     *
     * @return Docket
     */
    @Bean
    public Docket docket() {
        Predicate<RequestHandler> selector = RequestHandlerSelectors.basePackage("fr.agoero.controller");
        List<Response> reponseList = List.of(
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .description(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .representation(MediaType.APPLICATION_JSON)
                .apply(getApiErrorDto())
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .description(HttpStatus.FORBIDDEN.getReasonPhrase())
                .representation(MediaType.APPLICATION_JSON)
                .apply(getApiErrorDto())
                .build()
        );
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .protocols(
                Set.of(gatewayProperties.getProtocol()))
            .useDefaultResponseMessages(false)
            .globalResponses(HttpMethod.GET, reponseList)
            .globalResponses(HttpMethod.PUT, reponseList)
            .globalResponses(HttpMethod.POST, reponseList)
            .globalResponses(HttpMethod.PATCH, reponseList)
            .globalResponses(HttpMethod.DELETE, reponseList)
            .additionalModels(typeResolver.resolve(ApiErrorDTO.class))
            .select()
            .apis(selector)
            .paths(PathSelectors.any())
            .build();
    }

    private Consumer<RepresentationBuilder> getApiErrorDto() {
        return representationBuilder -> representationBuilder.model(
            modelSpecificationBuilder -> modelSpecificationBuilder.referenceModel(
                referenceModelSpecificationBuilder -> referenceModelSpecificationBuilder.key(
                    modelKeyBuilder -> modelKeyBuilder.qualifiedModelName(
                        qualifiedModelNameBuilder -> qualifiedModelNameBuilder.namespace(
                            "fr.agoero.exception").name("ApiErrorDTO")))));
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
