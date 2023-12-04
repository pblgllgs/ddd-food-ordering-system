package com.pblgllgs.order.service.domain.entity;

import com.pblgllgs.entity.AggregateRoot;
import com.pblgllgs.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {

    public Customer() {
    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
