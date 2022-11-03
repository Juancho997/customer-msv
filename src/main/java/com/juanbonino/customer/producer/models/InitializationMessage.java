package com.juanbonino.customer.producer.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
public class InitializationMessage {

    private String message;

    @JsonCreator
    public InitializationMessage(@JsonProperty("message") String message){
        this.message = message;
    }
}
