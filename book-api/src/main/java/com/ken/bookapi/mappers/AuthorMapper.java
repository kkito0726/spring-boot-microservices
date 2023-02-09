package com.ken.bookapi.mappers;

import com.ken.bookapi.dtos.AuthorDto;
import com.ken.bookapi.models.Author;
import com.ken.shared.domein.AuthorEventDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

  private final ModelMapper _modelMapper;

  public Author toEntity(AuthorEventDto dto) {
    return _modelMapper.map(dto, Author.class);
  }

  public AuthorDto toDto(Author entity) {
    return _modelMapper.map(entity, AuthorDto.class);
  }
}
