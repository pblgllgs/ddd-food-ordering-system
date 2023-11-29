package com.pblgllgs.ports.output.message.publisher.restaurantapproval;

import com.pblgllgs.event.publisher.DomainEventPublisher;
import com.pblgllgs.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
