package com.pblgllgs.kafka.producer.impl;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.kafka.producer.exception.KafkaProducerException;
import com.pblgllgs.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

@Component
@Slf4j
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            ListenableFuture<SendResult<K, V>> kafkaResultFuture = (ListenableFuture<SendResult<K, V>>) kafkaTemplate.send(topicName, key, message);
            kafkaResultFuture.addCallback(callback);
        } catch (Exception e) {
            log.info("Error on kafka producer with key: {}, message: {} and exception: {}", key, message, e.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: "+ key+ " and message: "+ message);
        }
    }

    @PreDestroy
    public void close(){
         if (kafkaTemplate != null){
             log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
         }
    }
}