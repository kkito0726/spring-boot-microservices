package com.ken.bookapi.services;

import com.ken.bookapi.dtos.AuthorDto;
import com.ken.bookapi.dtos.BookDto;
import com.ken.bookapi.dtos.CreateBookDto;
import com.ken.bookapi.dtos.UpdateBookDto;
import com.ken.bookapi.mappers.AuthorMapper;
import com.ken.bookapi.mappers.BookMapper;
import com.ken.bookapi.models.Author;
import com.ken.bookapi.models.Book;
import com.ken.bookapi.repositories.AuthorRepository;
import com.ken.bookapi.repositories.BookRepository;
import com.ken.shared.constants.RabbitMQKeys;
import com.ken.shared.domein.BookEventDto;
import com.ken.shared.errors.NotFoundException;
import com.ken.shared.models.CustomMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository _bookRepository;
  private final BookMapper _bookMapper;
  private final RabbitTemplate _template;
  private final AuthorRepository _authorRepository;
  private final AuthorMapper _authorMapper;

  @Override
  public List<BookDto> getBooks() {
    List<Book> books = _bookRepository.findAll();
    List<BookDto> bookDtos = books
      .stream()
      .map(book -> _bookMapper.toDto(book))
      .toList();
    return bookDtos;
  }

  @Override
  public BookDto getBook(UUID id) {
    Book book = _findBookId(id);
    List<Author> existingAuthors = _authorRepository.findAllById(
      book.getAuthors()
    );
    List<AuthorDto> authorDtos = existingAuthors
      .stream()
      .map(authorItem -> _authorMapper.toDto(authorItem))
      .toList();
    BookDto bookDto = _bookMapper.toDto(book);
    bookDto.setAuthors(authorDtos);

    return bookDto;
  }

  @Override
  public BookDto createBook(CreateBookDto dto) {
    Book newBook = new Book(
      dto.getTitle(),
      dto.getDescription(),
      dto.getAuthors()
    );
    Book savedBook = _bookRepository.save(newBook);
    CustomMessage<BookEventDto> message = new CustomMessage<>();
    message.setMessageId(UUID.randomUUID());
    message.setMessageDate(LocalDateTime.now());
    message.setPayload(_bookMapper.toEventDto(savedBook));
    _template.convertAndSend(RabbitMQKeys.BOOK_CREATED_EXCHANGE, null, message);
    return _bookMapper.toDto(savedBook);
  }

  @Override
  public void deleteBook(UUID id) {
    Book book = _findBookId(id);
    _bookRepository.delete(book);

    CustomMessage<BookEventDto> message = new CustomMessage<>();
    message.setMessageId(UUID.randomUUID());
    message.setMessageDate(LocalDateTime.now());
    message.setPayload(_bookMapper.toEventDto(book));

    _template.convertAndSend(RabbitMQKeys.BOOK_DELETED_EXCHANGE, null, message);
  }

  @Override
  public void updateBook(UpdateBookDto dto, UUID id) {
    Book found = _findBookId(id);

    if (Objects.nonNull(dto.getTitle())) {
      found.setTitle(dto.getTitle());
    }

    if (Objects.nonNull(dto.getDescription())) {
      found.setDescription(dto.getDescription());
    }

    _bookRepository.save(found);

    CustomMessage<BookEventDto> message = new CustomMessage<>();
    message.setMessageId(UUID.randomUUID());
    message.setMessageDate(LocalDateTime.now());
    message.setPayload(_bookMapper.toEventDto(found));

    _template.convertAndSend(RabbitMQKeys.BOOK_UPDATED_EXCHANGE, null, message);
  }

  private Book _findBookId(UUID id) {
    Optional<Book> result = _bookRepository.findById(id);
    if (result.isEmpty()) {
      throw new NotFoundException("Not found with this ID: " + id);
    }

    return result.get();
  }
}
