package fr.agoero.exception;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
// -2 to take priority over DefaultErrorWebExceptionHandler
@Order(-2)
@Slf4j
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
        ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
}

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request,true);
        // build ApiErrorDTO from Spring error
        var apiErrorDTO = new ApiErrorDTO(errorPropertiesMap);
        // log
        logError(apiErrorDTO);
        return ServerResponse.status(apiErrorDTO.getHttpStatusCode())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromValue(apiErrorDTO));
    }

    /**
     * Log error
     *
     * @param apiErrorDTO
     */
    private void logError(ApiErrorDTO apiErrorDTO) {
        log.warn(
            String.format(
                "HttpStatusCode %d HttpStatusMessage %s message %s ",
                apiErrorDTO.getHttpStatusCode(),
                apiErrorDTO.getHttpStatusLabel(),
                apiErrorDTO.getMessage()
            )
        );
    }

}
