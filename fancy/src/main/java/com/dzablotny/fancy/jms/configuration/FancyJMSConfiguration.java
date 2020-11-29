package com.dzablotny.fancy.jms.configuration;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import java.util.Collections;

@Configuration
public class FancyJMSConfiguration {
    @Value("${spring.jms.broker}")
    private String broker;
    @Value("${spring.jms.queue}")
    private String queue;

    @Bean(name = "FancyConnectionFactory")
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(broker);
        connectionFactory.setTrustedPackages(Collections.singletonList("com.dzablotny"));
        return connectionFactory;
    }

    @Bean(name = "FancyJMSTemplate")
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(queue);
        return template;
    }
}
