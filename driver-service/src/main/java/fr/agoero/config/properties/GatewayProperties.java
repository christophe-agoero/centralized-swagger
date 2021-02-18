package fr.agoero.config.properties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Gateway properties logger and validation
 */
@Validated
@Configuration
@ConfigurationProperties("gateway")
@Getter
@Setter
@ToString
public class GatewayProperties {

    @NotNull
    @Pattern(regexp = "https?")
    private String protocol;
    @NotNull
    private String host;
}
