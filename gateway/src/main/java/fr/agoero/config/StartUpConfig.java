package fr.agoero.config;

import fr.agoero.swagger.RefreshSwaggerStartUpRunner;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StartUpConfig {

    private final RefreshSwaggerStartUpRunner refreshSwaggerStartUpRunner;

    @PostConstruct
    public void checkRunBatchOnStartup() {
        refreshSwaggerStartUpRunner.runResfreshSwaggerOnStartupWithDelay();
    }

}
