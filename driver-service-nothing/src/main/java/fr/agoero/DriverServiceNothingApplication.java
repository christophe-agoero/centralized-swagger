package fr.agoero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DriverServiceNothingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverServiceNothingApplication.class, args);
    }
}
