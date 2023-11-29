package com.pblgllgs;
/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */

import com.pblgllgs.dto.create.CreateOrderCommand;
import com.pblgllgs.mapper.OrderDataMapper;
import com.pblgllgs.order.service.domain.OrderDomainService;
import com.pblgllgs.order.service.domain.entity.Customer;
import com.pblgllgs.order.service.domain.entity.Order;
import com.pblgllgs.order.service.domain.entity.Restaurant;
import com.pblgllgs.order.service.domain.event.OrderCreatedEvent;
import com.pblgllgs.order.service.domain.exception.OrderDomainException;
import com.pblgllgs.ports.output.repository.CustomerRepository;
import com.pblgllgs.ports.output.repository.OrderRepository;
import com.pblgllgs.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository, OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order orderResult = saveOrder(order);
        log.info("Order is created with id: {}", orderResult.getId());
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantInformation = restaurantRepository.findRestaurantInformation(restaurant);
        if (restaurantInformation.isEmpty()){
            log.warn("Couldn't find restaurant with id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Couldn't find restaurant with id: "+ createOrderCommand.getRestaurantId());
        }
        return restaurantInformation.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id: {}",customerId);
            throw new OrderDomainException("Could not find customer with id: " +customerId);
        }
    }

    private Order saveOrder(Order order){
        Order orderSaved = orderRepository.save(order);
        if (orderSaved == null) {
            throw new OrderDomainException("Couldn't save order!");
        }
        log.info("Order is saved with id: {}", orderSaved.getId());
        return orderSaved;
    }
}
