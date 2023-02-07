package com.ken.bookapi.mappers;

import com.ken.bookapi.dtos.BookDto;
import com.ken.bookapi.models.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

  private final ModelMapper _modelMapper;

  public BookDto toDto(Book entity) {
    return _modelMapper.map(entity, BookDto.class);
  }
}
