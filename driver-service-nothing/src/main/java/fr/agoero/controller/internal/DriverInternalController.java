package fr.agoero.controller.internal;

import static fr.agoero.exception.ApiExceptionEnum.DRIVER_NOT_FOUND;
import static fr.agoero.util.ConstantsAPI.INTERNAL_DRIVER_PATH;
import static fr.agoero.util.ConstantsAPI.PRICE_PATH;
import static fr.agoero.util.ConstantsAPI.RATING_PATH;
import static fr.agoero.util.DriverDataUtil.DRIVER_DTO_MAP;

import fr.agoero.dto.PatchPriceDTO;
import fr.agoero.dto.PatchRatingDTO;
import fr.agoero.exception.ApiErrorDTO;
import fr.agoero.exception.ApiException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(
    value = INTERNAL_DRIVER_PATH,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class DriverInternalController {

    /**
     * Patch the rating
     *
     * @param id
     * @param patchRatingDTO
     * @return Empty body
     */
    @PatchMapping("/{id}/" + RATING_PATH)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation("Update driver rating")
    @ApiResponses(
        {
            @ApiResponse(code = 404, message = "Driver not found", response = ApiErrorDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorDTO.class)
        }
    )
    public ResponseEntity<Void> patchRating(
        @ApiParam(required = true, value = "Driver identifier", example = "3") @PathVariable final int id,
        @Valid @RequestBody final PatchRatingDTO patchRatingDTO) {
        if (!DRIVER_DTO_MAP.containsKey(id)) {
            throw new ApiException(DRIVER_NOT_FOUND, String.valueOf(id));
        }
        DRIVER_DTO_MAP.get(id).setRating(patchRatingDTO.getRating());
        return ResponseEntity.noContent().build();
    }

    /**
     * Patch the price per hour
     *
     * @param id
     * @param patchPriceDTO
     * @return Empty body
     */
    @PatchMapping("/{id}/" + PRICE_PATH)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation("Update driver price per hour")
    @ApiResponses(
        {
            @ApiResponse(code = 404, message = "Driver not found", response = ApiErrorDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorDTO.class)
        }
    )
    public ResponseEntity<Void> patchPricePerHour(
        @ApiParam(required = true, value = "Driver identifier", example = "3") @PathVariable final int id,
        @Valid @RequestBody final PatchPriceDTO patchPriceDTO) {
        if (!DRIVER_DTO_MAP.containsKey(id)) {
            throw new ApiException(DRIVER_NOT_FOUND, String.valueOf(id));
        }
        DRIVER_DTO_MAP.get(id).setPricePerHour(patchPriceDTO.getPricePerHour());
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a driver
     *
     * @param id
     * @return Empty body
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation("Delete a driver by identifier")
    @ApiResponses(
        @ApiResponse(code = 404, message = "Driver not found", response = ApiErrorDTO.class)
    )
    public ResponseEntity<Void> delete(
        @ApiParam(required = true, value = "Driver identifier", example = "3") @PathVariable final int id) {
        DRIVER_DTO_MAP.remove(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all driver
     *
     * @return Empty body
     */
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation("Delete all drivers")
    public ResponseEntity<Void> deleteAll() {
        DRIVER_DTO_MAP.clear();
        return ResponseEntity.noContent().build();
    }

}
