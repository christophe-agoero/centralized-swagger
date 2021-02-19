package fr.agoero.config;

import static fr.agoero.swagger.SwaggerApiDocHolder.getServiceNameSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.*;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    public static final String API_DOC_PATH = "api/api_docs";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(
        InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            resources.clear();
            resources.addAll(getSwaggerResourceList());
            return resources;
        };
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                                     .operationsSorter(OperationsSorter.METHOD)
                                     .build();
    }

    private List<SwaggerResource> getSwaggerResourceList() {
        return getServiceNameSet().stream()
                                  .map(this::buildSwaggerResource)
                                  .collect(Collectors.toList());
    }

    private SwaggerResource buildSwaggerResource(String serviceName) {
        SwaggerResource resource = new SwaggerResource();
        resource.setLocation("/" + API_DOC_PATH + "/" + serviceName);
        resource.setName(serviceName);
        resource.setSwaggerVersion("2.0");
        return resource;
    }

}
