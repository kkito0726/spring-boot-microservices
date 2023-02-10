package com.ken.bookapi.listeners;

import com.ken.bookapi.config.RabbitMQConfig;
import com.ken.bookapi.mappers.AuthorMapper;
import com.ken.bookapi.models.Author;
import com.ken.bookapi.models.Book;
import com.ken.bookapi.repositories.AuthorRepository;
import com.ken.bookapi.repositories.BookRepository;
import com.ken.shared.domein.AuthorEventDto;
import com.ken.shared.models.CustomMessage;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthorDeletedEventListener {

  private final AuthorRepository _authorRepository;
  private final BookRepository _bookRepository;
  private final AuthorMapper _authorMapper;

  @RabbitListener(queues = { RabbitMQConfig.QUEUE_AUTHOR_DELETED })
  public void handleMessage(CustomMessage<AuthorEventDto> message) {
    log.info(
      "{} got triggered. got a message: {}",
      AuthorDeletedEventListener.class,
      message.toString()
    );
    AuthorEventDto authorEventDto = message.getPayload();

    Optional<Author> existingAuthor = _authorRepository.findById(
      authorEventDto.getId()
    );
    if (existingAuthor.isEmpty()) {
      return;
    }

    Author author = _authorMapper.toEntity(authorEventDto);
    _authorRepository.delete(author);

    List<Book> books = _bookRepository.findAllByAuthors(authorEventDto.getId());
    for (Book bookItem : books) {
      bookItem.getAuthors().remove(authorEventDto.getId());
    }
  }
}
