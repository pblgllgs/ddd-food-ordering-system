package com.pblgllgs.kafka.producer.exception;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

public class KafkaProducerException extends RuntimeException{

    public KafkaProducerException(String message) {
        super(message);
    }
}
