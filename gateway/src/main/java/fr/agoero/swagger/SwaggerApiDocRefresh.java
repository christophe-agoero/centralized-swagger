package fr.agoero.swagger;

import static fr.agoero.swagger.SwaggerApiDocHolder.clearApiDoc;
import static fr.agoero.swagger.SwaggerApiDocHolder.putApiDoc;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SwaggerApiDocRefresh {

    private static final String SWAGGER_ENABLED_KEY = "swaggerEnabled";
    private static final String SWAGGER_PATH_KEY = "swaggerPath";
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;


    private static boolean isSwaggerEnabled(InstanceInfo instanceInfo) {
        return isNotEmpty(instanceInfo.getMetadata())
            && Boolean.parseBoolean(instanceInfo.getMetadata()
                                                .getOrDefault(SWAGGER_ENABLED_KEY, String.valueOf(false)));
    }

    public void refreshSwaggerDoc() {
        log.info("Begin refresh swagger documentation");
        clearApiDoc();
        getAvailableDistictInstanceInfoList().forEach(instanceInfo -> {
            log.info("Service to refresh : " + instanceInfo.getVIPAddress());
            String apiDoc = getSwaggerDoc(instanceInfo);
            if (isNotBlank(apiDoc)) {
                putApiDoc(instanceInfo.getVIPAddress(), apiDoc);
                log.info(
                    String.format(
                        "Service %s refreshed at %s",
                        instanceInfo.getVIPAddress(),
                        ZonedDateTime.now(ZoneId.of("Europe/Paris"))
                    )
                );
            }
        });
        log.info("End refresh swagger config");
    }

    private String getSwaggerDoc(InstanceInfo instanceInfo) {
        String apiDoc = null;
        try {
            String docUrl = instanceInfo.getHomePageUrl() + instanceInfo.getMetadata().get(SWAGGER_PATH_KEY);
            apiDoc = restTemplate.getForObject(docUrl, String.class);
        } catch (Exception e) {
            log.error("Error during refresh documentation for service : " + instanceInfo.getVIPAddress(), e);
        }
        return apiDoc;
    }

    private List<InstanceInfo> getAvailableDistictInstanceInfoList() {
        Map<String, InstanceInfo> instanceInfoMap = new HashMap<>();
        eurekaClient.getApplications()
                    .getRegisteredApplications()
                    .forEach(
                        application ->
                            application.getInstances()
                                       .stream()
                                       .filter(SwaggerApiDocRefresh::isSwaggerEnabled)
                                       .forEach(instanceInfo -> instanceInfoMap.put(instanceInfo.getAppName(),
                                           instanceInfo))
                    );
        return new ArrayList<>(instanceInfoMap.values());
    }

}
