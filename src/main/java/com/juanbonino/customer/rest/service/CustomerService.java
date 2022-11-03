package com.juanbonino.customer.rest.service;

import com.juanbonino.customer.producer.event.Event;
import com.juanbonino.customer.producer.event.EventType;
import com.juanbonino.customer.producer.models.CustomerData;
import com.juanbonino.customer.producer.models.CustomerEvent;
import com.juanbonino.customer.rest.request.Request;
import com.juanbonino.customer.rest.entity.Customer;
import com.juanbonino.customer.rest.repository.CustomerRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, Event<?>> kafkaTemplate;

    public CustomerService(CustomerRepository customerRepository, KafkaTemplate<String, Event<?>> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Customer createCustomer(Request request){
        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.randomUUID().toString());
        newCustomer.setEmail(request.getEmail());
        newCustomer.setPassword(request.getPassword());
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findCustomerById(String id) {
       return customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User with id : ${id} was not found"));
    }

    public void removeCustomerById(String id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(String id, Request request) {
        return customerRepository.findById(id)
                .map(customer -> {
                            customer.setEmail(request.getEmail());
                            customer.setPassword(request.getPassword());
                            return customerRepository.save(customer);
                        }
                ).orElseThrow(()->new RuntimeException("User with id : ${id} was not found"));

    }

    public CustomerData createCustomerDataModel(Customer customer){
        CustomerData customerData = new CustomerData();
        customerData.setUserId(customer.getId());
        customerData.setUserEmail(customer.getEmail());
        return customerData;
    }

    public CustomerEvent createCustomerEvent(CustomerData customerData, EventType eventType){


        CustomerEvent createdCustomerEvent = new CustomerEvent();

        LocalDate dt = LocalDate.parse(LocalDate.now().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");


        createdCustomerEvent.setEventId(UUID.randomUUID().toString());
        createdCustomerEvent.setEventType(eventType);
        createdCustomerEvent.setEventDate(formatter.format(dt));
        createdCustomerEvent.setEventData(customerData);
        return createdCustomerEvent;
    }

    public void publishEvent(String topic, CustomerEvent createdCustomerEvent){
        kafkaTemplate.send(topic, createdCustomerEvent);
        System.out.println("MESSAGE SENT TO TOPIC : " + topic);
        System.out.println("EVENT TYPE : " + createdCustomerEvent.getEventType());
    }

}
