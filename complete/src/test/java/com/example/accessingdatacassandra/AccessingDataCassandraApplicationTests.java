package com.example.accessingdatacassandra;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.cassandra.CassandraInvalidQueryException;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.SpecificationBuilder;
import org.springframework.lang.NonNull;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class AccessingDataCassandraApplicationTests {

	@Autowired
	VetRepository vets;

	@Test
	void contextLoads() {
	}

	@Test
	void findByFirstNameWhenNotAllowFilteringThenFails() {
		assertThatExceptionOfType(CassandraInvalidQueryException.class)
			.isThrownBy(() -> this.vets.findByFirstName("somename"));
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

		@Override
		public SchemaAction getSchemaAction() {
			return SchemaAction.RECREATE_DROP_UNUSED;
		}

		@NonNull
		@Override
		protected String getKeyspaceName() {
			return this.properties.getKeyspaceName();
		}
	}
}
