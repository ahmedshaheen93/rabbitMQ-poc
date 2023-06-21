package com.shaheen.rabbitmqpoc.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  private final String host;
  private final int port;
  private final String username;
  private final String password;

  public RabbitMQConfig(@Value("${spring.rabbitmq.host}") String host,
                        @Value("${spring.rabbitmq.port}") int port,
                        @Value("${spring.rabbitmq.username}") String username,
                        @Value("${spring.rabbitmq.password}") String password) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
  }

  @Bean
  public Queue myQueue() {
    return new Queue("my_queue", true);
  }


  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }
  @Bean
  public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
    container.setDefaultRequeueRejected(false);
    // todo was not working at all

    // Set the number of concurrent consumers
//    container.setConcurrentConsumers(5);

//    // Enable long polling by setting the receiveTimeout
//    container.setReceiveTimeout(5000); // 5 seconds

//    // Enable message redelivery by configuring the retry policy
//    container.setRetryDeclarationInterval(5000); // 5 seconds
//    container.setFailedDeclarationRetryInterval(5000); // 5 seconds
    container.setAdviceChain(RetryInterceptorBuilder
        .stateless()
        .maxAttempts(3) // Maximum number of retry attempts
        .backOffOptions(50000, 2.0, 300000) // Initial interval, multiplier, and max interval
        .build());

    return container;
  }

}
