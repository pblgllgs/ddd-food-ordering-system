package com.pblgllgs;
/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */

import com.pblgllgs.dto.message.RestaurantApprovalResponse;
import com.pblgllgs.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    @Override
    public void orderAprroved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
