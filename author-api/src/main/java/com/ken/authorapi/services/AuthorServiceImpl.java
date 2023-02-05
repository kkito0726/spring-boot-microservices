package com.ken.authorapi.services;

import com.ken.authorapi.dtos.AuthorDto;
import com.ken.authorapi.dtos.CreateAuthorDto;
import com.ken.authorapi.dtos.UpdateAuthorDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

  @Override
  public List<AuthorDto> getAuthors() {
    return new ArrayList<AuthorDto>(
      Arrays.asList(
        new AuthorDto(UUID.randomUUID(), "author 1", "nice author 1"),
        new AuthorDto(UUID.randomUUID(), "author 2", "nice author 2"),
        new AuthorDto(UUID.randomUUID(), "author 3", "nice author 3")
      )
    );
  }

  @Override
  public AuthorDto getAuthor(UUID id) {
    return new AuthorDto(UUID.randomUUID(), "author 1", "nice author 1");
  }

  @Override
  public AuthorDto creAuthorDto(CreateAuthorDto dto) {
    return new AuthorDto(UUID.randomUUID(), "author 1", "nice author 1");
  }

  @Override
  public void deleteAuthor(UUID id) {}

  @Override
  public void updateAuthor(UpdateAuthorDto dto, UUID id) {}
}
