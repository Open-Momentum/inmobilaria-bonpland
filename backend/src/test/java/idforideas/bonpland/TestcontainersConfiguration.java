package idforideas.bonpland;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
class TestcontainersConfiguration {

		private static final MySQLContainer<?> mysqlContainer =
				new MySQLContainer<>("mysql:8.0.33")
						.withUsername("test")
						.withPassword("test")
						.withDatabaseName("bonpland");

			static {
				mysqlContainer.start();
				System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
				System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
				System.setProperty("spring.datasource.password", mysqlContainer.getPassword());

				System.setProperty("spring.flyway.url", mysqlContainer.getJdbcUrl());
				System.setProperty("spring.flyway.user", mysqlContainer.getUsername());
				System.setProperty("spring.flyway.password", mysqlContainer.getPassword());
			}

		}


