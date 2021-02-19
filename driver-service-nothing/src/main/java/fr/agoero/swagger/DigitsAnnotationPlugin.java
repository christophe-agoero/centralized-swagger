package fr.agoero.swagger;

import java.util.Optional;
import javax.validation.constraints.Digits;
import springfox.bean.validators.plugins.Validators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

public class DigitsAnnotationPlugin implements ModelPropertyBuilderPlugin {

    private static final String SPAN_BEGIN = "<font color=\"red\"><i><b>validation: ";
    private static final String SPAN_END = "</b></i></font>";

    @Override
    public void apply(ModelPropertyContext context) {
        extractAnotation(context).ifPresent(
            digits -> context.getSpecificationBuilder().description(getDescription(digits))
        );
    }

    private Optional<Digits> extractAnotation(ModelPropertyContext context) {
        return Validators.annotationFromBean(context, Digits.class)
                         .or(() -> Validators.annotationFromField(context, Digits.class));
    }

    private String getDescription(Digits digits) {
        return String.format(
            SPAN_BEGIN + "integer %d, fraction %d" + SPAN_END,
            digits.integer(), digits.fraction()
        );
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
