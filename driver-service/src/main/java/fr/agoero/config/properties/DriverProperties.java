package fr.agoero.config.properties;

import java.util.StringJoiner;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Driver properties logger
 */
@Configuration
@AllArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class DriverProperties {

    private final GatewayProperties gatewayProperties;

    @Override
    public String toString() {
        return new StringJoiner(
            "\n",
            "\n------------------ Log all properties begin ------------------\n\n",
            "\n\n------------------ Log all properties end ------------------")
            .add(gatewayProperties.toString())
            .toString();
    }

    @PostConstruct
    private void logProperties() {
        log.info(toString());
    }
}
