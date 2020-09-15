package com.piyush.jms.sender;

import static com.piyush.jms.config.JmsConfig.MY_QUEUE;
import static com.piyush.jms.config.JmsConfig.MY_SEND_RCV_QUEUE;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piyush.jms.model.HelloWorldMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Piyush
 *
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class HelloSender {

	private final JmsTemplate jmsTemplate;
	private final ObjectMapper objectMapper;

	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		log.info("Sending Message");
		HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello World").build();

		jmsTemplate.convertAndSend(MY_QUEUE, message);
		log.info("Message Sent");
	}

	@Scheduled(fixedRate = 2000)
	public void sendandReceiveMessage() throws JMSException {

		HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello").build();

		Message receviedMsg = jmsTemplate.sendAndReceive(MY_SEND_RCV_QUEUE, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message helloMessage = null;

				try {
					helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
					helloMessage.setStringProperty("_type", "com.piyush.jms.model.HelloWorldMessage");

					log.info("Sending Hello");

					return helloMessage;

				} catch (JsonProcessingException e) {
					throw new JMSException("boom");
				}
			}
		});

		log.info("Received message reply from queue " + receviedMsg.getBody(HelloWorldMessage.class));

	}

}
