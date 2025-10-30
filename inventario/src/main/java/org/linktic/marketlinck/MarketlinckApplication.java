package org.linktic.marketlinck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MarketlinckApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketlinckApplication.class, args);
    }

}
