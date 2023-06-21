package com.shaheen.rabbitmqpoc.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public MessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String message) {
    rabbitTemplate.convertAndSend("my_queue", message);
    System.out.println("Message sent: " + message);
  }
}
