package fr.agoero.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "Driver")
public class DriverDTO {

    @ApiModelProperty(example = "3")
    private int id;
    @ApiModelProperty(example = "2")
    private int rating;
    @ApiModelProperty(example = "333.33")
    private BigDecimal pricePerHour;
    @ApiModelProperty(example = "1960-03-21T12:00:00.000Z")
    private ZonedDateTime bitrhDate;
    @ApiModelProperty(example = "Ayrton")
    private String firstName;
    @ApiModelProperty(example = "Senna")
    private String lastName;
    @ApiModelProperty(example = "+33 9 87 65 43 21")
    private String phoneNumber;
    @ApiModelProperty(example = "LOUIS_BMX_11")
    private String licenseNumber;
}
