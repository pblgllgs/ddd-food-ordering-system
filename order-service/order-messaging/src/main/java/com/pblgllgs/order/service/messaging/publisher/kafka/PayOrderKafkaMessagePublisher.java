package com.pblgllgs.order.service.messaging.publisher.kafka;
/*
 *
 * @author pblgl
 * Created on 01-12-2023
 *
 */

import com.pblgllgs.config.OrderServiceConfigData;
import com.pblgllgs.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.pblgllgs.kafka.producer.service.KafkaProducer;
import com.pblgllgs.order.service.domain.event.OrderPaidEvent;
import com.pblgllgs.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.pblgllgs.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(
            OrderMessagingDataMapper orderMessagingDataMapper,
            OrderServiceConfigData orderServiceConfigData,
            KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer,
            OrderKafkaMessageHelper orderKafkaMessageHelper
    ) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                    orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);
            kafkaProducer.send(
                    orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(
                            orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            restaurantApprovalRequestAvroModel,
                            orderId,
                            "RestaurantApprovalRequestAvroModel"
                    )
            );
            log.info("RestaurantApprovalRequestAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.info(
                    "Error while sending RestaurantApprovalRequestAvroModel message to kafka with order id : {}, error: {}",
                    orderId,
                    e.getMessage()
            );
        }
    }
}
