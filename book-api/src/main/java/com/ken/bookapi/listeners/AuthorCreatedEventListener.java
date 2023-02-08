package com.ken.bookapi.listeners;

import com.ken.bookapi.config.RabbitMQConfig;
import com.ken.shared.domein.AuthorEventDto;
import com.ken.shared.models.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorCreatedEventListener {

  @RabbitListener(queues = { RabbitMQConfig.QUEUE_AUTHOR_CREATED })
  public void handleMessage(CustomMessage<AuthorEventDto> message) {
    log.info(
      "{} got triggered. got a message: {}",
      AuthorCreatedEventListener.class,
      message.toString()
    );
  }
}
