package com.ken.bookapi.listeners;

import com.ken.bookapi.config.RabbitMQConfig;
import com.ken.bookapi.models.Author;
import com.ken.bookapi.repositories.AuthorRepository;
import com.ken.shared.domein.AuthorEventDto;
import com.ken.shared.models.CustomMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorUpdatedEventListener {

  private final AuthorRepository _authorRepository;

  @RabbitListener(queues = { RabbitMQConfig.QUEUE_AUTHOR_UPDATED })
  public void handleMessage(CustomMessage<AuthorEventDto> message) {
    log.info(
      "{} got triggered. got a message: {}",
      AuthorUpdatedEventListener.class,
      message.toString()
    );
    AuthorEventDto authorEventDto = message.getPayload();
    Optional<Author> existingAuthor = _authorRepository.findById(
      authorEventDto.getId()
    );

    if (existingAuthor.isEmpty()) {
      return;
    }

    Author author = existingAuthor.get();
    author.setName(authorEventDto.getName());
    author.setDescription(authorEventDto.getDescription());
    _authorRepository.save(author);
  }
}
