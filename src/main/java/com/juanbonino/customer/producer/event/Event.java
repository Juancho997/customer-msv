package com.juanbonino.customer.producer.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event<T>{
    private String eventId;
    private String eventDate;
    private EventType eventType;
    private T eventData;
}
