package com.juanbonino.customer;

import com.juanbonino.customer.producer.Producer;
import com.juanbonino.customer.producer.models.InitializationMessage;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(Producer producer){
		return (args) -> {
			producer.send(new InitializationMessage("Cloud Karafka Producer initialized"));
		};
	}
}
