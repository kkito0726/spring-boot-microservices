package com.ken.bookapi.services;

import com.ken.bookapi.dtos.BookDto;
import com.ken.bookapi.dtos.CreateBookDto;
import com.ken.bookapi.dtos.UpdateBookDto;
import java.util.List;
import java.util.UUID;

public interface BookService {
  public List<BookDto> getBooks();

  public BookDto getBook(UUID id);

  public BookDto createBook(CreateBookDto dto);

  public void deleteBook(UUID id);

  public void updateBook(UpdateBookDto dto, UUID id);
}
