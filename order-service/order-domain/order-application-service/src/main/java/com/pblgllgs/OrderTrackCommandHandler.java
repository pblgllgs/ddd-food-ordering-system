package com.pblgllgs;

import com.pblgllgs.dto.track.TrackOrderQuery;
import com.pblgllgs.dto.track.TrackOrderResponse;
import com.pblgllgs.mapper.OrderDataMapper;
import com.pblgllgs.order.service.domain.entity.Order;
import com.pblgllgs.order.service.domain.exception.OrderNotFoundException;
import com.pblgllgs.order.service.domain.valueobject.TrackingId;
import com.pblgllgs.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */
@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery){
        Optional<Order> orderResult = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (orderResult.isEmpty()) {
            log.info("Couldn't find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Couldn't find order with tracking id: "+ trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
