package fr.agoero.controller;

import static fr.agoero.config.SwaggerConfig.API_DOC_PATH;
import static fr.agoero.swagger.SwaggerApiDocHolder.getApiDoc;

import fr.agoero.swagger.SwaggerApiDocRefresh;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/" + API_DOC_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SwaggerController {

    private final SwaggerApiDocRefresh swaggerApiDocRefresh;

    @GetMapping("/{serviceName}")
    public String getServiceDescription(@PathVariable("serviceName") String serviceName) {
        return getApiDoc(serviceName);
    }

    @PutMapping("/refresh")
    public String refreshServiceDescription() {
        swaggerApiDocRefresh.refreshSwaggerDoc();
        return "Finish see log for more information";
    }
}
