package com.dzablotny.morgue.jms.receiver;

import com.dzablotny.legacy.soap.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class JMSReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(JMSReceiver.class);

    @Autowired
    @Qualifier("MorgueJMSTemplate")
    JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("MorgueConverter")
    MessageConverter messageConverter;

    public Patient receivePatient() {
        try {
            Message message = jmsTemplate.receive();
            if (message != null) {
                return (Patient) messageConverter.fromMessage(message);
            } else {
                throw new JMSException("No value in received message!");
            }
        } catch (JMSException e) {
            LOGGER.error(String.format(
                    "JMSReceiver, %s",
                    e
            ));
        }
        return null;
    }
}
