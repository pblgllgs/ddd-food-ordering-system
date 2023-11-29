package com.pblgllgs.ports.output.message.publisher.payment;

import com.pblgllgs.event.publisher.DomainEventPublisher;
import com.pblgllgs.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
