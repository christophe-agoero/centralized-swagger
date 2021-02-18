package fr.agoero.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverDTO {

    private int id;
    private int rating;
    private BigDecimal pricePerHour;
    private ZonedDateTime bitrhDate;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String licenseNumber;
}
