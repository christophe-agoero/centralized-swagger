package fr.agoero.dto;

import static fr.agoero.util.ConstantsAPI.DRIVER_DTO_LIST_JSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverListDTO {

    @JsonProperty(DRIVER_DTO_LIST_JSON)
    private List<DriverDTO> driverDTOList = new ArrayList<>();
    private int total;
}
