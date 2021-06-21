package com.example.accessingdatacassandra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraTemplate;

@SpringBootApplication
public class AccessingDataCassandraApplication {

	private final static Logger log = LoggerFactory.getLogger(AccessingDataCassandraApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AccessingDataCassandraApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner clr(VetRepository vetRepository, CassandraTemplate cassandraTemplate) {
		return args -> {
			
			Vet vet = new Vet(UUID.randomUUID(), "Sergi", "Almar", new HashSet<>(Arrays.asList("surgery")));
			
			Vet savedVet = vetRepository.save(vet);

			vetRepository.findAll()
				.forEach(v -> log.info("Vet: {}", v.getFirstName()));
			
			vetRepository.findById(savedVet.getId())
				.ifPresent(v -> log.info("Vet by id. {}", v.getFirstName()));
		};
	}
}
