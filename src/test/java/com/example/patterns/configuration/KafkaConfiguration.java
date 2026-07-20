package com.example.patterns.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration(proxyBeanMethods = false)
public class KafkaConfiguration {
    //    public static final String PROCESSED_EVENTS_TOPIC_NAME = "processed.events";
    public static final String OUTBOX_CHECKOUT_COMPLETED_EVENT = "checkout.completed";
    public static final String OUTBOX_CHECKOUT_EVENTS = "outbox_events";


    @Bean
    NewTopic checkOutCompletedTopic() {
        return TopicBuilder
                .name(OUTBOX_CHECKOUT_COMPLETED_EVENT)
                .replicas(1)
                .partitions(3)
                .build();
    }

    @Bean
    NewTopic checkoutOutboxEvents() {
        return TopicBuilder.name(OUTBOX_CHECKOUT_EVENTS)
                .replicas(1)
                .partitions(3)
                .build();
    }
}
