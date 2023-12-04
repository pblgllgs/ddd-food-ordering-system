package com.pblgllgs.order.service.dataaccess.order.mapper;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.pblgllgs.order.service.dataaccess.order.entity.OrderEntity;
import com.pblgllgs.order.service.dataaccess.order.entity.OrderItemEntity;
import com.pblgllgs.order.service.domain.entity.Order;
import com.pblgllgs.order.service.domain.entity.OrderItem;
import com.pblgllgs.order.service.domain.entity.Product;
import com.pblgllgs.order.service.domain.valueobject.OrderItemId;
import com.pblgllgs.order.service.domain.valueobject.StreetAddress;
import com.pblgllgs.order.service.domain.valueobject.TrackingId;
import com.pblgllgs.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.pblgllgs.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .customerId(order.getCustomerId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(
                        order.getFailureMessages() != null
                                ?
                                String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages())
                                :
                                ""
                )
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntiryToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(
                        orderEntity.getFailureMessages().isEmpty()
                                ?
                                new ArrayList<>()
                                :
                                new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(FAILURE_MESSAGE_DELIMITER)))
                )
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream().map(
                item -> OrderItem.builder()
                        .orderItemId(new OrderItemId(item.getId()))
                        .product(new Product(new ProductId(item.getProductId())))
                        .price(new Money(item.getPrice()))
                        .quantity(item.getQuantity())
                        .subTotal(new Money(item.getSubTotal()))
                        .build()
        ).collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream().map(
                orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build()
        ).collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity())
                .build();
    }
}
