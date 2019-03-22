package com.advance.concepts.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConnectionConfig {

    @Value("${rabbit.queue.username}")
    private String userName;
    @Value("${rabbit.queue.password}")
    private String password;
    @Value("${rabbit.queue.host}")
    private String hostName;
    @Value("${rabbit.queue.port}")
    private int portNumber;


    // @Bean
    private ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        if (StringUtils.isNotBlank(userName)) {
            factory.setUsername(userName);
        }
        if (StringUtils.isNotBlank(password)) {
            factory.setPassword(password);
        }
        if (StringUtils.isNotBlank(hostName)) {
            factory.setHost(hostName);
        } else {
            factory.setHost("localhost");
        }
        if (portNumber > 0) {
            factory.setPort(portNumber);
        }
        factory.setAutomaticRecoveryEnabled(true);
        return factory;
    }

    @Bean
    // public Connection getConnection(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
    public Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory().newConnection();
    }


}
