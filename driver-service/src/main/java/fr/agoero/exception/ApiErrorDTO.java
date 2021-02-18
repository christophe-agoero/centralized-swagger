package fr.agoero.exception;


import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Common DTO error
 */
@Getter
@NoArgsConstructor
public class ApiErrorDTO implements Serializable {

    private String timestamp;
    private int httpStatusCode;
    private String httpStatusLabel;
    private String code;
    private String message;
    private String details;

     public ApiErrorDTO(int httpStatusCode, String httpStatusLabel, String code, String message) {
        this.timestamp = ZonedDateTime.now()
                                      .withZoneSameInstant(ZoneOffset.UTC)
                                      .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.httpStatusCode = httpStatusCode;
        this.httpStatusLabel = httpStatusLabel;
        this.code = code;
        this.message = message;
    }

    ApiErrorDTO(int httpStatusCode, String httpStatusLabel, String code, String message, String details) {
        this(httpStatusCode, httpStatusLabel, code, message);
        this.details = details;
    }

}
