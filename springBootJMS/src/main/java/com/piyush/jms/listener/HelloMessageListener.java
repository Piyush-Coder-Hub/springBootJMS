package com.piyush.jms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.piyush.jms.config.JmsConfig;
import com.piyush.jms.model.HelloWorldMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Piyush
 *
 */

@Component
@Slf4j
public class HelloMessageListener {
	
	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage,
					   @Headers MessageHeaders headers, Message message) {
		
		log.info("Message recieved");
		log.info(helloWorldMessage.toString());

	}
}
