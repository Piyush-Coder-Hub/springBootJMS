package com.piyush.jms.sender;

import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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
@Slf4j
@Component
public class HelloSender {

	private final JmsTemplate jmsTemplate;

	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		log.info("Sending Message");
		HelloWorldMessage message = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("Hello World")
				.build();
		
		jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,message);
		log.info("Message Sent");
	}

}
