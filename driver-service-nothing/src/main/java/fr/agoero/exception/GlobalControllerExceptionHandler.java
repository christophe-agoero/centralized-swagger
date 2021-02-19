package fr.agoero.exception;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.net.BindException;
import java.util.List;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handling for controller
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    private static final String TYPE_MISMATCH_MESSAGE = "Type mismatch, failed to convert %s to requiered type %s";
    private static final String ARGUMENT_NOT_VALID_MESSAGE = "Argument not valid, object %s field %s  error : %s";
    private static final List<String> COLLECTION_TYPE = List.of("List", "Set", "Map", "Tab");

    /**
     * Manage functional
     *
     * @param apiException
     * @return ResponseEntity
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorDTO> handleApiException(ApiException apiException) {
        var msgLog =
            String.format(
                "HttpStatusCode : %d - HttpStatusMessage : %s -  Code : %s - Details : %s",
                apiException.getHttpStatus().value(),
                apiException.getHttpStatus().getReasonPhrase(),
                apiException.getApiErrorDTO().getCode(),
                apiException.getApiErrorDTO().getDetails()
            );
        log.warn(msgLog);
        return new ResponseEntity<>(
            apiException.getApiErrorDTO(),
            apiException.getHttpStatus()
        );
    }

    /**
     * Manage METHOD_NOT_ALLOWED
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException exception) {
        var headers = new HttpHeaders();
        if (isNotEmpty(exception.getSupportedHttpMethods())) {
            headers.setAllow(exception.getSupportedHttpMethods());
        }
        return buildResponseEntity(
            HttpStatus.METHOD_NOT_ALLOWED,
            exception,
            headers
        );
    }

    /**
     * Manage UNSUPPORTED_MEDIA_TYPE
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException exception) {
        var headers = new HttpHeaders();
        List<MediaType> mediaTypes = exception.getSupportedMediaTypes();
        if (isNotEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
        }
        return buildResponseEntity(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            exception,
            headers
        );
    }

    /**
     * Manage NOT_ACCEPTABLE
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
        HttpMediaTypeNotAcceptableException exception) {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            exception
        );
    }

    /**
     * Manage BAD_REQUEST
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler({
        MissingServletRequestParameterException.class,
        ServletRequestBindingException.class,
        TypeMismatchException.class,
        HttpMessageNotReadableException.class,
        MethodArgumentNotValidException.class,
        MissingServletRequestPartException.class,
        BindException.class,
        IllegalArgumentException.class,
        ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception exception) {
        return buildResponseEntity(
            HttpStatus.BAD_REQUEST,
            exception
        );
    }

    /**
     * Manage NOT_FOUND
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        return buildResponseEntity(
            HttpStatus.NOT_FOUND,
            exception
        );
    }

    /**
     * Manage FORBIDDEN
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        return buildResponseEntity(
            HttpStatus.FORBIDDEN,
            exception
        );
    }

    /**
     * Manage UNAUTHORIZED
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        return buildResponseEntity(
            HttpStatus.UNAUTHORIZED,
            exception
        );
    }

    /**
     * Manage other exception
     *
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        return buildResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception
        );
    }


    /**
     * Build response without header
     *
     * @param httpStatus
     * @param exception
     * @return ResponseEntity
     */
    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception exception) {
        return buildResponseEntity(
            httpStatus,
            exception,
            null
        );
    }

    /**
     * Build response
     *
     * @param httpStatus
     * @param exception
     * @param headers
     * @return ResponseEntity
     */
    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception exception,
        HttpHeaders headers) {
        String message = transformMessageAccordingExceptionType(exception);
        log.warn(
            String.format(
                "HttpStatusCode %d HttpStatusMessage %s message %s ",
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message
            )
        );
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            null,
            message
        );
        return new ResponseEntity<>(apiErrorDTO, headers, httpStatus);
    }

    /**
     * Transform the message according to the exception type
     *
     * @param exception
     * @Return message
     */
    private String transformMessageAccordingExceptionType(Exception exception) {
        String message = exception.getMessage();
        try {
            if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
                message = buildArgumentNotValidMessage(methodArgumentNotValidException, message);
            } else if (exception instanceof TypeMismatchException typeMismatchException) {
                message = buildTypeMismatchMessage(typeMismatchException, message);
            } else if (exception instanceof ConstraintViolationException constraintViolationException) {
                message = buildArgumentNotValidMessage(constraintViolationException, message);
            }
        } catch (Exception e) {
            // for sonnar, keep the base mesage
            log.debug("", e);
        }
        return message;
    }

    /**
     * Build TYPE_MISMATCH_MESSAGE from TypeMismatchException
     *
     * @param typeMismatchException
     * @param message
     * @return TYPE_MISMATCH_MESSAGE
     */
    private String buildTypeMismatchMessage(TypeMismatchException typeMismatchException, String message) {
        if (typeMismatchException.getValue() != null
            && typeMismatchException.getRequiredType() != null) {
            message =
                String.format(
                    TYPE_MISMATCH_MESSAGE,
                    typeMismatchException.getValue(),
                    typeMismatchException.getRequiredType().getSimpleName()
                );
        }
        return message;
    }

    /**
     * Build ARGUMENT_NOT_VALID_MESSAGE from ConstraintViolationException
     *
     * @param constraintViolationException
     * @param message
     * @return ARGUMENT_NOT_VALID_MESSAGE
     */
    private String buildArgumentNotValidMessage(ConstraintViolationException constraintViolationException,
        String message) {
        var constraintViolation = constraintViolationException.getConstraintViolations().iterator().next();
        if (constraintViolation != null
            && isNotBlank(constraintViolation.getPropertyPath().toString())
            && isNotBlank(constraintViolation.getRootBeanClass().getSimpleName())
        ) {
            message =
                buildArgumentNotValidMessage(
                    constraintViolation.getRootBeanClass().getSimpleName(),
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getMessage()
                );
        }
        return message;
    }

    /**
     * Build ARGUMENT_NOT_VALID_MESSAGE from MethodArgumentNotValidException
     *
     * @param methodArgumentNotValidException
     * @param message
     * @return ARGUMENT_NOT_VALID_MESSAGE
     */
    private String buildArgumentNotValidMessage(
        MethodArgumentNotValidException methodArgumentNotValidException, String message) {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError != null
            && isNotBlank(fieldError.getObjectName())
            && isNotBlank(fieldError.getField())
            && isNotBlank(fieldError.getDefaultMessage())
        ) {
            message =
                buildArgumentNotValidMessage(
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
                );
        }
        return message;
    }


    /**
     * Build ARGUMENT_NOT_VALID_MESSAGE
     *
     * @param objectName
     * @param fieldName
     * @param originalMessage
     * @return ARGUMENT_NOT_VALID_MESSAGE
     */
    private String buildArgumentNotValidMessage(String objectName, String fieldName, String originalMessage) {
        String message;
        message =
            String.format(
                ARGUMENT_NOT_VALID_MESSAGE,
                replaceCollectionTypeByPlural(objectName),
                replaceCollectionTypeByPlural(fieldName),
                originalMessage
            );
        return message;
    }

    /**
     * Replace collection type by s
     *
     * @param stringToReplace
     * @return the input string with replacement of the collection type by s
     */
    private String replaceCollectionTypeByPlural(String stringToReplace) {
        for (String type : COLLECTION_TYPE) {
            stringToReplace = stringToReplace.replace(type, "s");
        }
        return stringToReplace;
    }
}
