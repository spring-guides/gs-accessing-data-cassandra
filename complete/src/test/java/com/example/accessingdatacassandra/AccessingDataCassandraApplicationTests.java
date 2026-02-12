package com.example.accessingdatacassandra;

import org.junit.jupiter.api.Test;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.cassandra.CassandraInvalidQueryException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class AccessingDataCassandraApplicationTests {

	@Container
	@ServiceConnection
	static CassandraContainer container = new CassandraContainer(DockerImageName.parse("cassandra:3.11.10"))
			.withInitScript("create-keyspace.cql");

	@Autowired
	private VetRepository vets;

	@Test
	void findByFirstNameWhenNotAllowFilteringThenFails() {
		assertThatExceptionOfType(CassandraInvalidQueryException.class)
				.isThrownBy(() -> this.vets.findByFirstName("somename"));
	}

}
