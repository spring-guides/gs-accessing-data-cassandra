package com.example.accessingdatacassandra;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface VetRepository extends CassandraRepository<Vet, UUID> {	
	Vet findByFirstName(String username);
}