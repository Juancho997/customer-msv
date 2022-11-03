package com.juanbonino.customer.producer.models;

import com.juanbonino.customer.producer.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerEvent extends Event<CustomerData> {
}
