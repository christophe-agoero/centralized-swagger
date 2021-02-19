package fr.agoero.config.properties;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Swagger properties logger and validation
 */
@Validated
@Configuration
@ConfigurationProperties("swagger")
@Getter
@Setter
@ToString
public class SwaggerProperties {

    @NotNull
    private String contactName;
    private String contactUrl;
    @NotNull
    private String contactEmail;
    @NotNull
    private String description;
    private String license;
    private String licenseUrl;
    private String termsOfServiceUrl;
    @NotNull
    private String title;
    private boolean displayAll;

}
