package fr.agoero.controller.external;

import static fr.agoero.exception.ApiExceptionEnum.DRIVER_NOT_FOUND;
import static fr.agoero.util.ConstantsAPI.EXTERNAL_DRIVER_PATH;
import static fr.agoero.util.DriverDataUtil.DEFAULT_PRICE_PER_HOUR;
import static fr.agoero.util.DriverDataUtil.DEFAULT_RATING;
import static fr.agoero.util.DriverDataUtil.DRIVER_DTO_MAP;

import fr.agoero.config.properties.GatewayProperties;
import fr.agoero.dto.DriverDTO;
import fr.agoero.dto.DriverListDTO;
import fr.agoero.dto.SaveDriverDTO;
import fr.agoero.exception.ApiException;
import java.net.URI;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(
    value = EXTERNAL_DRIVER_PATH
)
@RequiredArgsConstructor
public class DriverExternalController {

    private final GatewayProperties gatewayProperties;

    /**
     * Find driver by id
     *
     * @param id
     * @return DriverDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> findById(@PathVariable final int id) {
        if (!DRIVER_DTO_MAP.containsKey(id)) {
            throw new ApiException(DRIVER_NOT_FOUND, String.valueOf(id));
        }
        return ResponseEntity.ok(DRIVER_DTO_MAP.get(id));
    }

    /**
     * Find all driver
     *
     * @return DriverDTO
     */
    @GetMapping()
    public ResponseEntity<DriverListDTO> findAll() {
        return ResponseEntity.ok(
            new DriverListDTO(new ArrayList<>(DRIVER_DTO_MAP.values()), DRIVER_DTO_MAP.size())
        );
    }

    /**
     * Create driver
     *
     * @param saveDriverDTO
     * @return Response with Header Location and empty body
     */
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody final SaveDriverDTO saveDriverDTO) {
        // add driver in map
        DriverDTO driverDTO = DriverDTO.builder()
                                       .bitrhDate(saveDriverDTO.getBitrhDate())
                                       .firstName(saveDriverDTO.getFirstName())
                                       .id(DRIVER_DTO_MAP.keySet()
                                                         .stream()
                                                         .mapToInt(Integer::intValue)
                                                         .max()
                                                         .orElseThrow(NoSuchElementException::new) + 1)
                                       .lastName(saveDriverDTO.getLastName())
                                       .licenseNumber(saveDriverDTO.getLicenseNumber())
                                       .phoneNumber(saveDriverDTO.getPhoneNumber())
                                       .pricePerHour(DEFAULT_PRICE_PER_HOUR)
                                       .rating(DEFAULT_RATING).build();
        DRIVER_DTO_MAP.put(driverDTO.getId(), driverDTO);
        // build Location uri
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                             .scheme(gatewayProperties.getProtocol())
                                             .host(gatewayProperties.getHost())
                                             .port(null)
                                             .path("/{id}")
                                             .buildAndExpand(driverDTO.getId())
                                             .toUri();
        return ResponseEntity.created(uri).build();
    }

}
