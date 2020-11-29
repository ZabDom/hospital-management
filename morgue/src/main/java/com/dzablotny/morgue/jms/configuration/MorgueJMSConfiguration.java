package com.dzablotny.morgue.jms.configuration;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import java.util.Collections;

@Configuration
public class MorgueJMSConfiguration {
    @Value("${spring.jms.broker}")
    private String broker;
    @Value("${spring.jms.queue}")
    private String queue;

    @Bean(name = "MorgueConnectionFactory")
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(broker);
        connectionFactory.setTrustedPackages(Collections.singletonList("com.dzablotny"));
        return connectionFactory;
    }

    @Bean(name = "MorgueJMSTemplate")
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(queue);
        return template;
    }

    @Bean(name = "MorgueConverter")
    MessageConverter converter() {
        return new SimpleMessageConverter();
    }
}
