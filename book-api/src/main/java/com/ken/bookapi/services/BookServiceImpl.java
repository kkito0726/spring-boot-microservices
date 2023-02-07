package com.ken.bookapi.services;

import com.ken.bookapi.dtos.BookDto;
import com.ken.bookapi.dtos.CreateBookDto;
import com.ken.bookapi.dtos.UpdateBookDto;
import com.ken.bookapi.mappers.BookMapper;
import com.ken.bookapi.models.Book;
import com.ken.bookapi.repositories.BookRepository;
import com.ken.shared.errors.NotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository _bookRepository;
  private final BookMapper _bookMapper;

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
    BookDto bookDto = _bookMapper.toDto(book);

    return bookDto;
  }

  @Override
  public BookDto createBook(CreateBookDto dto) {
    Book newBook = new Book(dto.getTitle(), dto.getDescription());
    Book savedBook = _bookRepository.save(newBook);
    return _bookMapper.toDto(savedBook);
  }

  @Override
  public void deleteBook(UUID id) {
    Book book = _findBookId(id);
    _bookRepository.delete(book);
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
  }

  private Book _findBookId(UUID id) {
    Optional<Book> result = _bookRepository.findById(id);
    if (result.isEmpty()) {
      throw new NotFoundException("Not found with this ID: " + id);
    }

    return result.get();
  }
}
