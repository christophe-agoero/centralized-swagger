package fr.agoero.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@ApiModel(description = "Driver to save")
public class SaveDriverDTO {

    @NotNull
    @ApiModelProperty(required = true, example = "1955-02-24T11:00:00.000Z")
    private ZonedDateTime bitrhDate;
    @Size(min = 2, max = 20)
    @ApiModelProperty(example = "Alain")
    private String firstName;
    @NotNull
    @Size(min = 3, max = 30)
    @ApiModelProperty(required = true, example = "Prost")
    private String lastName;
    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$")
    @ApiModelProperty(example = "+33 9 87 65 43 21")
    private String phoneNumber;
    @NotNull
    @ApiModelProperty(required = true, example = "LOUIS_BMX_11")
    private String licenseNumber;
}
