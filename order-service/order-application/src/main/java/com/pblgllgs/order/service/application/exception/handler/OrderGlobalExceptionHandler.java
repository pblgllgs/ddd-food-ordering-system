package com.pblgllgs.order.service.application.exception.handler;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.application.handler.ErrorDTO;
import com.pblgllgs.application.handler.GlobalExceptionHandler;
import com.pblgllgs.order.service.domain.exception.OrderDomainException;
import com.pblgllgs.order.service.domain.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {OrderDomainException.class})
    @ResponseBody
    public ErrorDTO handlerException(OrderDomainException orderDomainException){
        log.error(orderDomainException.getLocalizedMessage(),orderDomainException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(orderDomainException.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {OrderNotFoundException.class})
    @ResponseBody
    public ErrorDTO handlerException(OrderNotFoundException orderNotFoundException){
        log.error(orderNotFoundException.getLocalizedMessage(),orderNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(orderNotFoundException.getMessage())
                .build();
    }

}
