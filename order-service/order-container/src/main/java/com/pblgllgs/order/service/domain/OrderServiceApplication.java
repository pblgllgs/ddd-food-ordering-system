package com.pblgllgs.order.service.domain;
/*
 *
 * @author pblgl
 * Created on 04-12-2023
 *
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.pblgllgs.order.service.dataaccess")
@EntityScan(basePackages = "com.pblgllgs.order.service.dataaccess")
@SpringBootApplication(scanBasePackages = "com.pblgllgs")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class,args);
    }
}
