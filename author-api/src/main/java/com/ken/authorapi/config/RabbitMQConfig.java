package com.ken.authorapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ken.shared.constants.RabbitMQKeys;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  // producer
  @Bean
  public FanoutExchange authorCreatedExchange() {
    return new FanoutExchange(RabbitMQKeys.AUTHOR_CREATED_EXCHANGE);
  }

  @Bean
  public FanoutExchange authorUpdatedExchange() {
    return new FanoutExchange(RabbitMQKeys.AUTHOR_UPDATE_EXCHANGE);
  }

  @Bean
  public FanoutExchange authorDeletedExchange() {
    return new FanoutExchange(RabbitMQKeys.AUTHOR_DELETED_EXCHANGE);
  }

  @Bean
  public MessageConverter messageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return new Jackson2JsonMessageConverter(objectMapper);
  }

  @Bean
  public AmqpTemplate template(
    ConnectionFactory connectionFactory,
    MessageConverter messageConverter
  ) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter);

    return template;
  }
}