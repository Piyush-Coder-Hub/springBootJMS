package com.piyush.jms.listener;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.piyush.jms.config.JmsConfig;
import com.piyush.jms.model.HelloWorldMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Piyush
 *
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class HelloMessageListener {

	private final JmsTemplate jmsTemplate;

	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers,
			org.springframework.messaging.Message message) {

		log.info("Message recieved");
		log.info(helloWorldMessage.toString());

	}

	@JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
	public void listenForHello(// @Payload HelloWorldMessage helloWorldMessage,
			@Headers MessageHeaders headers, Message message) throws JMSException {

		HelloWorldMessage payloadMsg = HelloWorldMessage.builder().id(UUID.randomUUID()).message("World!!").build();

		jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);

	}
}
