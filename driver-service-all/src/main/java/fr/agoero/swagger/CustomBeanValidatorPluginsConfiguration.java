package fr.agoero.swagger;

import org.springframework.context.annotation.Bean;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

public class CustomBeanValidatorPluginsConfiguration extends BeanValidatorPluginsConfiguration {

    @Bean
    public DigitsAnnotationPlugin digitsAnnotationPlugin() {
        return new DigitsAnnotationPlugin();
    }

    @Bean
    public SwaggerWarningAnnotationPlugin swaggerWarningAnnotationPlugin() {
        return new SwaggerWarningAnnotationPlugin();
    }

}
