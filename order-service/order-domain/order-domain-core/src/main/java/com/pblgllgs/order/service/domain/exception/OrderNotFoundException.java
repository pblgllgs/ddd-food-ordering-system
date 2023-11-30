package com.pblgllgs.order.service.domain.exception;
/*
 *
 * @author pblgl
 * Created on 29-11-2023
 *
 */

import com.pblgllgs.exception.DomainException;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
