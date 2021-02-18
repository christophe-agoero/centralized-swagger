package fr.agoero.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveDriverDTO {

    @NotNull
    private ZonedDateTime bitrhDate;
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 30)
    private String lastName;
    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$")
    private String phoneNumber;
    @NotNull
    private String licenseNumber;
}
