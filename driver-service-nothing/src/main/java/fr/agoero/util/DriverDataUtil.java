package fr.agoero.util;

import fr.agoero.dto.DriverDTO;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverDataUtil {

    public static final BigDecimal DEFAULT_PRICE_PER_HOUR = new BigDecimal("222.22");
    public static final int DEFAULT_RATING = 3;
    public static final Map<Integer, DriverDTO> DRIVER_DTO_MAP = new HashMap<>();

    static {
        DRIVER_DTO_MAP.put(1, DriverDTO.builder()
                                       .bitrhDate(ZonedDateTime.now())
                                       .firstName("driver1_fistName")
                                       .id(1)
                                       .lastName("driver1_lastName")
                                       .licenseNumber("driver1_licenseNumber")
                                       .phoneNumber("01 23 45 67 89")
                                       .pricePerHour(new BigDecimal("99.99"))
                                       .rating(2)
                                       .build());
        DRIVER_DTO_MAP.put(2, DriverDTO.builder()
                                       .bitrhDate(ZonedDateTime.now())
                                       .firstName("driver2_fistName")
                                       .id(2)
                                       .lastName("driver2_lastName")
                                       .licenseNumber("driver2_licenseNumber")
                                       .phoneNumber("01-23-45-67-89")
                                       .pricePerHour(new BigDecimal("599.25"))
                                       .rating(3)
                                       .build());
        DRIVER_DTO_MAP.put(3, DriverDTO.builder()
                                       .bitrhDate(ZonedDateTime.now())
                                       .firstName("driver3_fistName")
                                       .id(3)
                                       .lastName("driver3_lastName")
                                       .licenseNumber("driver3_licenseNumber")
                                       .phoneNumber("+33 1 23 45 67 89")
                                       .pricePerHour(new BigDecimal("999.12"))
                                       .rating(4)
                                       .build());
    }
}
