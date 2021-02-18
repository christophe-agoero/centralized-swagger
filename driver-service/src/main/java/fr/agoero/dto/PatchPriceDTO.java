package fr.agoero.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel(description = "Driver price per hour")
public class PatchPriceDTO {

    @NotNull
    @Digits(integer = 3, fraction = 2)
    @ApiModelProperty(required = true, example = "999.99")
    private BigDecimal pricePerHour;
}
