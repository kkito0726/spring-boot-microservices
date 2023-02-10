package com.ken.bookapi.listeners;

import com.ken.bookapi.config.RabbitMQConfig;
import com.ken.bookapi.models.Author;
import com.ken.bookapi.models.Book;
import com.ken.bookapi.repositories.AuthorRepository;
import com.ken.bookapi.repositories.BookRepository;
import com.ken.shared.domein.AuthorEventDto;
import com.ken.shared.models.CustomMessage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorUpdatedEventListener {

  private final AuthorRepository _authorRepository;
  private final BookRepository _bookRepository;

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

    List<Book> oldBooks = _bookRepository.findAllByAuthors(
      authorEventDto.getId()
    );
    List<UUID> newBookIds = authorEventDto.getBooks();
    List<Book> newBooks = _bookRepository.findAllById(newBookIds);

    for (Book newBookItem : newBooks) {
      if (!newBookItem.getAuthors().contains(authorEventDto.getId())) {
        newBookItem.getAuthors().add(authorEventDto.getId());
      }
    }

    for (Book oldBookItem : oldBooks) {
      if (!newBookIds.contains(oldBookItem.getId())) {
        oldBookItem.getAuthors().remove(authorEventDto.getId());
      }
    }
  }
}
