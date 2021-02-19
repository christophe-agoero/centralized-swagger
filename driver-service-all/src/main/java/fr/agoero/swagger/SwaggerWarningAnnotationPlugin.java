package fr.agoero.swagger;

import java.util.Optional;
import springfox.bean.validators.plugins.Validators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

public class SwaggerWarningAnnotationPlugin implements ModelPropertyBuilderPlugin {

    private static final String SPAN_BEGIN = "<font color=\"orange\"><i><b>warn: ";
    private static final String SPAN_END = "</b></i></font>";

    @Override
    public void apply(ModelPropertyContext context) {
        extractAnnotation(context).ifPresent(
            swaggerWarning -> context.getSpecificationBuilder().description(getDescription(swaggerWarning)));
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    private Optional<SwaggerWarning> extractAnnotation(ModelPropertyContext context) {
        return Validators.annotationFromBean(context, SwaggerWarning.class)
                         .or(() -> Validators.annotationFromField(context, SwaggerWarning.class));
    }

    private String getDescription(SwaggerWarning swaggerWarning) {
        return SPAN_BEGIN + swaggerWarning.description() + SPAN_END;
    }
}
