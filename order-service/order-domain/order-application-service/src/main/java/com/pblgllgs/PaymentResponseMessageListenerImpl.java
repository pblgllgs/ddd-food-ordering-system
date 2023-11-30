package com.pblgllgs;
/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */

import com.pblgllgs.dto.message.PaymentResponse;
import com.pblgllgs.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}
