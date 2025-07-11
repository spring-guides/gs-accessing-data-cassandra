package com.example.accessingdatacassandra;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.SpecificationBuilder;
import org.springframework.lang.NonNull;

@SpringBootTest
class AccessingDataCassandraApplicationTests {

	@Test
	void contextLoads() {
	}

	@TestConfiguration
	static class CreateKeyspaceConfiguration extends AbstractCassandraConfiguration {

		private final CassandraProperties properties;

		CreateKeyspaceConfiguration(CassandraProperties properties) {
			this.properties = properties;
		}

		@NonNull
		@Override
		protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
			CreateKeyspaceSpecification specification = SpecificationBuilder
					.createKeyspace(getKeyspaceName()).ifNotExists()
					.withSimpleReplication(1);

			return List.of(specification);
		}

		@NonNull
		@Override
		protected String getKeyspaceName() {
			return this.properties.getKeyspaceName();
		}
	}
}
