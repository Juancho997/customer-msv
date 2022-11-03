package com.juanbonino.customer.rest.repository;

import com.juanbonino.customer.rest.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {


}
