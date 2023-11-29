package com.pblgllgs.ports.input.service;

import com.pblgllgs.dto.create.CreateOrderCommand;
import com.pblgllgs.dto.create.CreateOrderResponse;
import com.pblgllgs.dto.track.TrackOrderQuery;
import com.pblgllgs.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
