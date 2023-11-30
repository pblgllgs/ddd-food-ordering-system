package com.pblgllgs;
/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */

import com.pblgllgs.order.service.domain.OrderDomainService;
import com.pblgllgs.order.service.domain.OrderDomainServiceImpl;
import com.pblgllgs.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.pblgllgs.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.pblgllgs.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.pblgllgs.ports.output.repository.CustomerRepository;
import com.pblgllgs.ports.output.repository.OrderRepository;
import com.pblgllgs.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.pblgllgs")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher(){
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher(){
        return  Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher(){
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository(){
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository(){
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }

}
