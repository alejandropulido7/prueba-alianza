package com.co.backend.infraestructure.adapters.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class ConnectionConfigR2dbc extends AbstractR2dbcConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "mysql")
                .option(HOST, "db-mysql")
                .option(PORT, 3306)
                .option(DATABASE, "alianza")
                .option(USER, "admin")
                .option(PASSWORD, "alianza")
                .build();

        ConnectionFactory connection = ConnectionFactories.get(options);

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(connection)
                .build();

        return new ConnectionPool(poolConfiguration);
    }
}
