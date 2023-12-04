package com.pblgllgs.order.service.dataaccess.customer.entity;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_customer_m_view", schema = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerEntity {

    @Id
    private UUID id;

}
