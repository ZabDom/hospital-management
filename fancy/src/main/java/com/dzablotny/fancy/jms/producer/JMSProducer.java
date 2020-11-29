package com.dzablotny.fancy.jms.producer;

import com.dzablotny.legacy.soap.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JMSProducer {

    @Autowired
    @Qualifier("FancyJMSTemplate")
    JmsTemplate jmsTemplate;

    public void sendPatient(Patient patient) {
        jmsTemplate.send(session -> session.createObjectMessage(patient));
    }
}