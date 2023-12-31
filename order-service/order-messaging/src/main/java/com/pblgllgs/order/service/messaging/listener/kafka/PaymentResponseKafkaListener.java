package com.pblgllgs.order.service.messaging.listener.kafka;
/*
 *
 * @author pblgl
 * Created on 01-12-2023
 *
 */

import com.pblgllgs.kafka.consumer.KafkaConsumer;
import com.pblgllgs.kafka.order.avro.model.PaymentResponseAvroModel;
import com.pblgllgs.kafka.order.avro.model.PaymentStatus;
import com.pblgllgs.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.pblgllgs.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public PaymentResponseKafkaListener(PaymentResponseMessageListener paymentResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.paymentResponseMessageListener = paymentResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${order-service.payment-response-topic-name}"
    )
    public void receive(
            @Payload List<PaymentResponseAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        log.info(
                "{} number of payment responses received with keys: {}, partitions: {} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString()
        );
        messages.forEach(paymentResponseAvroModel -> {
            if (PaymentStatus.COMPLETED == paymentResponseAvroModel.getPaymentStatus()) {
                log.info("Processing successful payment for order id: {}", paymentResponseAvroModel.getOrderId());
                paymentResponseMessageListener.paymentCompleted(
                        orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
                );
            } else if (
                    PaymentStatus.CANCELLED == paymentResponseAvroModel.getPaymentStatus() ||
                            PaymentStatus.FAILED == paymentResponseAvroModel.getPaymentStatus()
            ) {
                log.info("Processing unsuccessful payment for order id: {}", paymentResponseAvroModel.getOrderId());
                paymentResponseMessageListener.paymentCancelled(
                        orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
                );
            }
        });
    }
}
