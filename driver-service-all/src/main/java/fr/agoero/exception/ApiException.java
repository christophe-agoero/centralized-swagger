package fr.agoero.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * Common functional exception
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ApiErrorDTO apiErrorDTO;

    public ApiException(ApiExceptionEnum apiExceptionEnum, String details) {
        super(apiExceptionEnum.getMessage());
        this.httpStatus = apiExceptionEnum.getHttpStatus();
        this.apiErrorDTO = new ApiErrorDTO(
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            apiExceptionEnum.getCode(),
            apiExceptionEnum.getMessage(),
            details
        );
    }

}
