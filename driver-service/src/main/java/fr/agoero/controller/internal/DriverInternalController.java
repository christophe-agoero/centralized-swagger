package fr.agoero.controller.internal;

import static fr.agoero.exception.ApiExceptionEnum.DRIVER_NOT_FOUND;
import static fr.agoero.util.ConstantsAPI.INTERNAL_DRIVER_PATH;
import static fr.agoero.util.ConstantsAPI.PRICE_PATH;
import static fr.agoero.util.ConstantsAPI.RATING_PATH;
import static fr.agoero.util.DriverDataUtil.DRIVER_DTO_MAP;

import fr.agoero.dto.PatchPriceDTO;
import fr.agoero.dto.PatchRatingDTO;
import fr.agoero.exception.ApiException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(
    value = INTERNAL_DRIVER_PATH
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
    public ResponseEntity<Void> patchRating(@PathVariable final int id,
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
    public ResponseEntity<Void> patchPricePerHour(@PathVariable final int id,
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
    public ResponseEntity<Void> delete(@PathVariable final int id) {
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
    public ResponseEntity<Void> deleteAll() {
        DRIVER_DTO_MAP.clear();
        return ResponseEntity.noContent().build();
    }

}
