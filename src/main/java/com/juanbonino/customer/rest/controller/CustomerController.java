package com.juanbonino.customer.rest.controller;

import com.juanbonino.customer.producer.models.CustomerData;
import com.juanbonino.customer.producer.models.CustomerEvent;
import com.juanbonino.customer.producer.event.EventType;
import com.juanbonino.customer.rest.request.Request;
import com.juanbonino.customer.rest.entity.Customer;
import com.juanbonino.customer.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Value("${cloudkarafka.topic}")
    private String topic;

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping
    ResponseEntity<Customer> createCustomer(@RequestBody Request request){
        Customer newCustomer = customerService.createCustomer(request);
        CustomerData newCustomerData = customerService.createCustomerDataModel(newCustomer);
        CustomerEvent createdCustomerEvent = customerService.createCustomerEvent(newCustomerData, EventType.CREATED);
        customerService.publishEvent(topic, createdCustomerEvent);
        System.out.println("New Customer saved successfully in DB : " + newCustomer.getEmail());
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    ResponseEntity updateCustomer(@PathVariable String id, @RequestBody Request request){
        Customer updatedCustomer = customerService.updateCustomer(id, request);
        CustomerData updatedCustomerData = customerService.createCustomerDataModel(updatedCustomer);
        CustomerEvent updatedCustomerEvent = customerService.createCustomerEvent(updatedCustomerData, EventType.UPDATED);
        customerService.publishEvent(topic, updatedCustomerEvent);
        System.out.println("Customer updated successfully in DB : " + updatedCustomer.getEmail());
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    ResponseEntity deleteCustomer(@PathVariable String id){
        Customer foundCustomer = customerService.findCustomerById(id);
        customerService.removeCustomerById(id);
        CustomerData deletedCustomerData = customerService.createCustomerDataModel(foundCustomer);
        CustomerEvent deletedCustomerEvent = customerService.createCustomerEvent(deletedCustomerData, EventType.DELETED);
        customerService.publishEvent(topic, deletedCustomerEvent);
        System.out.println("Customer successfully deleted from DB : " + foundCustomer.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}