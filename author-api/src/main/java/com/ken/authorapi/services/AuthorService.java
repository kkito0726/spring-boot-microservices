package com.ken.authorapi.services;

import com.ken.authorapi.dtos.AuthorDto;
import com.ken.authorapi.dtos.CreateAuthorDto;
import com.ken.authorapi.dtos.UpdateAuthorDto;
import java.util.List;
import java.util.UUID;

public interface AuthorService {
  public List<AuthorDto> getAuthors();

  public AuthorDto getAuthor(UUID id);

  public AuthorDto createAuthorDto(CreateAuthorDto dto);

  public void deleteAuthor(UUID id);

  public void updateAuthor(UpdateAuthorDto dto, UUID id);
}
