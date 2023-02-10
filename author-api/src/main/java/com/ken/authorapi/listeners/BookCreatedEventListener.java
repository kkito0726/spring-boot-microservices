package com.ken.authorapi.listeners;

import com.ken.authorapi.config.RabbitMQConfig;
import com.ken.authorapi.mappers.BookMapper;
import com.ken.authorapi.models.Author;
import com.ken.authorapi.models.Book;
import com.ken.authorapi.repositories.AuthorRepository;
import com.ken.authorapi.repositories.BookRepository;
import com.ken.shared.domein.BookEventDto;
import com.ken.shared.models.CustomMessage;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookCreatedEventListener {

  private final AuthorRepository _authorRepository;
  private final BookRepository _bookRepository;
  private final BookMapper _bookMapper;

  @RabbitListener(queues = RabbitMQConfig.QUEUE_BOOK_CREATED)
  public void handleMessage(CustomMessage<BookEventDto> message) {
    BookEventDto bookEventDto = message.getPayload();
    log.info(
      "{} got triggered. got a message: {}",
      BookCreatedEventListener.class,
      message.toString()
    );

    Book newBook = _bookMapper.toEntity(bookEventDto);
    _bookRepository.save(newBook);

    // authorIdの追加
    List<UUID> authorIds = bookEventDto.getAuthors();
    if (Objects.isNull(authorIds) || authorIds.size() == 0) {
      return;
    }

    List<Author> authors = _authorRepository.findAllById(authorIds);

    for (Author authorItem : authors) {
      if (!authorItem.getBooks().contains(bookEventDto.getId())) {
        authorItem.getBooks().add(bookEventDto.getId());
      }
    }
  }
}
