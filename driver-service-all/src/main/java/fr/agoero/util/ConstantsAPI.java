package fr.agoero.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for the API
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsAPI {

    // PATH
    public static final String API_VERSION = "v1";
    public static final String DRIVER_SERVICE_PATH = "driver_service";
    public static final String DRIVER_PATH = "drivers";
    public static final String EXTERNAL_DRIVER_PATH = "/" + API_VERSION + "/" + DRIVER_SERVICE_PATH
        + "/" + DRIVER_PATH;
    public static final String INTERNAL_PATH = "internal";
    public static final String INTERNAL_DRIVER_PATH = "/" + INTERNAL_PATH + EXTERNAL_DRIVER_PATH;
    public static final String RATING_PATH = "rating";
    public static final String PRICE_PATH = "price";
    // BODY
    public static final String DRIVER_DTO_LIST_JSON = "drivers";
}
