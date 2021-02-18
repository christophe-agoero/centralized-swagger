package fr.agoero.exception;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import lombok.Getter;


/**
 * Common DTO error
 */
@Getter
public class ApiErrorDTO {

    private final String timestamp;
    private final int httpStatusCode;
    private final String httpStatusLabel;
    private final String code = null;
    private final String message;
    private final String details = null;

    /**
     * ApiErrorDTO from errorPropertiesMap
     *
     * @param errorPropertiesMap
     */
    public ApiErrorDTO(Map<String, Object> errorPropertiesMap) {
        this.timestamp = ZonedDateTime.ofInstant(((Date)errorPropertiesMap.get("timestamp")).toInstant(), ZoneOffset.UTC).toString();
        this.httpStatusCode = (int) errorPropertiesMap.get("status");
        this.httpStatusLabel = String.valueOf(errorPropertiesMap.get("error"));
        this.message = String.valueOf(errorPropertiesMap.get("message"));
    }

}
