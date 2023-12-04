package com.pblgllgs.order.service.dataaccess.customer.mapper;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.order.service.dataaccess.customer.entity.CustomerEntity;
import com.pblgllgs.order.service.domain.entity.Customer;
import com.pblgllgs.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityTOcustomer(CustomerEntity customerEntity){
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
