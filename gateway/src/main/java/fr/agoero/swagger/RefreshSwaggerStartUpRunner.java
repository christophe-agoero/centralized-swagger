package fr.agoero.swagger;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshSwaggerStartUpRunner {

    private static final Long OTHER_MICROSERVICE_WAIT = 30L;
    private final SwaggerApiDocRefresh swaggerApiDocRefresh;

    @Async
    public void runResfreshSwaggerOnStartupWithDelay() {
        log.info(
            String.format(
                "Refresh swagger not run immediately on statup wait %ds for registring in Eureka",
                OTHER_MICROSERVICE_WAIT
            )
        );
        sleepWaitOtherMicroservice(OTHER_MICROSERVICE_WAIT);
        log.info("Refresh swagger run with delay startup");
        swaggerApiDocRefresh.refreshSwaggerDoc();
    }

    private void sleepWaitOtherMicroservice(long sleepTime) {
        boolean sleep = true;
        try {
            while (sleep) {
                TimeUnit.SECONDS.sleep(sleepTime);
                sleep = false;
            }
        } catch (InterruptedException e) {
            log.error("sleep KO");
            Thread.currentThread()
                  .interrupt();
        }
    }

}
