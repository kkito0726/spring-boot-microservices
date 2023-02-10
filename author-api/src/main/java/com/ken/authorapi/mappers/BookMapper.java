package com.ken.authorapi.mappers;

import com.ken.authorapi.dtos.BookDto;
import com.ken.authorapi.models.Book;
import com.ken.shared.domein.BookEventDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

  private final ModelMapper _modelMapper;

  public BookDto toDto(Book book) {
    return _modelMapper.map(book, BookDto.class);
  }

  public Book toEntity(BookEventDto eventDto) {
    return _modelMapper.map(eventDto, Book.class);
  }
}
