package com.pblgllgs.order.service.dataaccess.order.adapter;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.pblgllgs.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.pblgllgs.order.service.domain.entity.Order;
import com.pblgllgs.order.service.domain.valueobject.TrackingId;
import com.pblgllgs.ports.output.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntiryToOrder(orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue()).map(orderDataAccessMapper::orderEntiryToOrder);
    }
}
