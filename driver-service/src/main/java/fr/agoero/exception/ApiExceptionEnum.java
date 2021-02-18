package fr.agoero.exception;


import static fr.agoero.exception.DriverExceptionConstant.DRIVER_NOT_FOUND_CODE;
import static fr.agoero.exception.DriverExceptionConstant.DRIVER_NOT_FOUND_LABEL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * Common functional enum exception
 */
@AllArgsConstructor
@Getter
public enum ApiExceptionEnum {

    // DRIVER
    // not found
    DRIVER_NOT_FOUND(HttpStatus.NOT_FOUND, DRIVER_NOT_FOUND_CODE, DRIVER_NOT_FOUND_LABEL),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
