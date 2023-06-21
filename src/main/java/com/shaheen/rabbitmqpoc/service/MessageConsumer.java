package com.shaheen.rabbitmqpoc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaheen.rabbitmqpoc.model.HelloModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {
  int numOfTrials;

  @RabbitListener(queues = "my_queue")
  public void receiveMessage(String message) {
    numOfTrials++;

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      HelloModel helloModel = objectMapper.readValue(message, HelloModel.class);
      if (numOfTrials % 2 == 0) {
        throw new RuntimeException("failed to consume message" + helloModel.getMessageId());
      } else {
        log.info("Received message: {}", message);
      }
    } catch (Exception e) {
      log.error("failed to consume message");
      throw new RuntimeException("Error");
    }
    // Perform necessary actions with the received message
  }
}
