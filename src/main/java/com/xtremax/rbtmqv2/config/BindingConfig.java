package com.xtremax.rbtmqv2.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {

  @Bean
  public Queue orderQueue() {
    return new Queue(MessagingConfig.QUEUE_ORDER);
  }

  @Bean
  public Queue paymentQueue() {
    return new Queue(MessagingConfig.QUEUE_CASHIER);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(MessagingConfig.EXCHANGE_NAME);
  }

  @Bean
  public Binding bindingOrder(Queue orderQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(orderQueue).to(topicExchange).with(MessagingConfig.ROUTE_ORDER);
  }

  @Bean
  public Binding bindingCashier(Queue paymentQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(paymentQueue).to(topicExchange).with(MessagingConfig.ROUTE_CASHIER);
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  /** It said..
   * In this template we can use it for :
   * - publish an event
   * - or publish the message to queue
   * - and consume it
   **/
  @Bean
  public AmqpTemplate template(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }
}
