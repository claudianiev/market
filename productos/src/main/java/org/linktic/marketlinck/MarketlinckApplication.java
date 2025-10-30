package org.linktic.marketlinck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class MarketlinckApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketlinckApplication.class, args);
    }
}