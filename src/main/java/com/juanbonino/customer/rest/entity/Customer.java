package com.juanbonino.customer.rest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private String id;
    @Column
    private String email;
    @Column
    private String password;
}
