package com.pblgllgs.order.service.domain;
/*
 *
 * @author pblgl
 * Created on 04-12-2023
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
