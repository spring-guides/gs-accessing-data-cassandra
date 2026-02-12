package com.example.accessingdatacassandra;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataCassandraApplication {

	private final static Logger log = LoggerFactory.getLogger(AccessingDataCassandraApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataCassandraApplication.class, args);
	}

	@Bean
	public CommandLineRunner clr(VetRepository vetRepository) {
		return args -> {
			vetRepository.deleteAll();

			Vet john = new Vet(UUID.randomUUID(), "John", "Doe", new HashSet<>(List.of("surgery")));
			Vet jane = new Vet(UUID.randomUUID(), "Jane", "Doe", new HashSet<>(List.of("radiology, surgery")));

			Vet savedJohn = vetRepository.save(john);
			vetRepository.save(jane);

			vetRepository.findAll()
					.forEach(v -> log.info("Vet: {}", v.getFirstName()));

			vetRepository.findById(savedJohn.getId())
					.ifPresent(v -> log.info("Vet by id: {}", v.getFirstName()));
		};
	}
}
