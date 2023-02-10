package com.ken.authorapi.listeners;

import com.ken.authorapi.config.RabbitMQConfig;
import com.ken.authorapi.models.Author;
import com.ken.authorapi.models.Book;
import com.ken.authorapi.repositories.AuthorRepository;
import com.ken.authorapi.repositories.BookRepository;
import com.ken.shared.domein.BookEventDto;
import com.ken.shared.models.CustomMessage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookUpdatedEventListener {

  private final AuthorRepository _authorRepository;
  private final BookRepository _bookRepository;

  @RabbitListener(queues = { RabbitMQConfig.QUEUE_BOOK_UPDATED })
  public void handleMessage(CustomMessage<BookEventDto> message) {
    log.info(
      "{} got triggered. got a message: {}",
      BookUpdatedEventListener.class,
      message.toString()
    );
    BookEventDto bookEventDto = message.getPayload();
    Optional<Book> existingBook = _bookRepository.findById(
      bookEventDto.getId()
    );

    if (existingBook.isEmpty()) {
      return;
    }

    Book book = existingBook.get();
    book.setTitle(bookEventDto.getTitle());
    book.setDescription(bookEventDto.getDescription());
    // _bookRepository.save(book);

    List<Author> oldAuthors = _authorRepository.findAllByBooks(
      bookEventDto.getId()
    );
    List<UUID> newAuthorIds = bookEventDto.getAuthors();
    List<Author> newAuthors = _authorRepository.findAllById(newAuthorIds);

    for (Author newAuthorItem : newAuthors) {
      if (!newAuthorItem.getBooks().contains(bookEventDto.getId())) {
        newAuthorItem.getBooks().add(bookEventDto.getId());
      }
    }

    for (Author oldAuthorItem : oldAuthors) {
      if (!newAuthorIds.contains(oldAuthorItem.getId())) {
        oldAuthorItem.getBooks().remove(bookEventDto.getId());
      }
    }
  }
}
