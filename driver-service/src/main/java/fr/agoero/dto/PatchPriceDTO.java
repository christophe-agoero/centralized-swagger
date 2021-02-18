package fr.agoero.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatchPriceDTO {

    @NotNull
    @Digits(integer = 3, fraction = 2)
    private BigDecimal pricePerHour;
}
