package com.pblgllgs.event.publisher;

import com.pblgllgs.event.DomainEvent;

public interface DomainEventPublisher < T extends DomainEvent> {

    void publish(T domainEvent);
}
