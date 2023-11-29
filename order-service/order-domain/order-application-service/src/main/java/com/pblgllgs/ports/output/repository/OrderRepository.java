package com.pblgllgs.ports.output.repository;

import com.pblgllgs.order.service.domain.entity.Order;
import com.pblgllgs.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save (Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
