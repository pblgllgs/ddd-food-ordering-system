package com.pblgllgs.order.service.domain.event;

import com.pblgllgs.event.DomainEvent;
import com.pblgllgs.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;

    private final ZonedDateTime createdAt;


    protected OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
