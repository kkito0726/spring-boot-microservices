package com.ken.authorapi.mappers;

import com.ken.authorapi.dtos.AuthorDto;
import com.ken.authorapi.models.Author;
import com.ken.shared.domein.AuthorEventDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

  private final ModelMapper _modelMapper;

  public AuthorDto toDto(Author entity) {
    return _modelMapper.map(entity, AuthorDto.class);
  }

  public AuthorEventDto toEventDto(Author entity) {
    return _modelMapper.map(entity, AuthorEventDto.class);
  }
}
