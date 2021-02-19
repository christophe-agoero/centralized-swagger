package fr.agoero.dto;

import fr.agoero.swagger.SwaggerWarning;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel(description = "Driver rating")
public class PatchRatingDTO {

    @NotNull
    @Min(1)
    @Max(5)
    @ApiModelProperty(required = true, example = "3")
    @SwaggerWarning(description = "1 is the best rating")
    private Integer rating;
}
