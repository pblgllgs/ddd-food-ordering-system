package com.pblgllgs.mapper;
/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */

import com.pblgllgs.dto.create.CreateOrderCommand;
import com.pblgllgs.dto.create.CreateOrderResponse;
import com.pblgllgs.dto.create.OrderAddress;
import com.pblgllgs.order.service.domain.entity.Order;
import com.pblgllgs.order.service.domain.entity.OrderItem;
import com.pblgllgs.order.service.domain.entity.Product;
import com.pblgllgs.order.service.domain.entity.Restaurant;
import com.pblgllgs.order.service.domain.valueobject.StreetAddress;
import com.pblgllgs.valueobject.CustomerId;
import com.pblgllgs.valueobject.Money;
import com.pblgllgs.valueobject.ProductId;
import com.pblgllgs.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return
                Restaurant.builder()
                        .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                        .products(createOrderCommand.getItems().stream().map(orderItem ->
                                new Product(new ProductId(orderItem.getProductId()))).collect(Collectors.toList())
                        )
                        .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemsEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse ordertoCreateOrderResponse(Order order){
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntities(List<com.pblgllgs.dto.create.OrderItem> orderITems) {
        return orderITems.stream()
                .map(
                        orderItem -> OrderItem
                                .builder()
                                .product(new Product(new ProductId(orderItem.getProductId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()
                ).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
