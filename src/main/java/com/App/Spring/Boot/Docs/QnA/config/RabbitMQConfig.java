package com.App.Spring.Boot.Docs.QnA.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {
    public static final String DOCUMENT_QUEUE = "documentIngestionQueue";

    @Bean
    public Queue documentQueue() {
        return new Queue(DOCUMENT_QUEUE, true);
    }
}
