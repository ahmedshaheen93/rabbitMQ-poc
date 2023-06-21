package com.shaheen.rabbitmqpoc.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
  @RabbitListener(queues = "my_queue")
  public void receiveMessage(String message) {
    System.out.println("Received message: " + message);
    // Perform necessary actions with the received message
  }
}
