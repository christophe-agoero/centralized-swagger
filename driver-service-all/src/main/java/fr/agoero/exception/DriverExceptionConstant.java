package fr.agoero.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Exception constant for API
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverExceptionConstant {

    // DRIVER
    // not found
    public static final String DRIVER_NOT_FOUND_CODE = "driver_not_found";
    public static final String DRIVER_NOT_FOUND_LABEL = "driver not found";

}
