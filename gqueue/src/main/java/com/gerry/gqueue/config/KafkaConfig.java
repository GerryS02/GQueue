package com.gerry.gqueue.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaConfig {
    
    @Bean
    public NewTopic topic(){
        
        return TopicBuilder.name("order-events").build();
    }
}
