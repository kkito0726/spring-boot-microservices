package com.ken.authorapi.services;

import com.ken.authorapi.dtos.AuthorDto;
import com.ken.authorapi.dtos.CreateAuthorDto;
import com.ken.authorapi.dtos.UpdateAuthorDto;
import com.ken.authorapi.models.Author;
import com.ken.authorapi.repositories.AuthorRepository;
import com.ken.shared.errors.NotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository _authorRepository;

  @Override
  public List<AuthorDto> getAuthors() {
    List<Author> authors = _authorRepository.findAll();
    List<AuthorDto> authorDtos = authors
      .stream()
      .map(authorItem ->
        (AuthorDto) AuthorDto
          .builder()
          .id(authorItem.getId())
          .name(authorItem.getName())
          .description(authorItem.getDescription())
          .createdAt(authorItem.getCreatedAt())
          .updatedAt(authorItem.getUpdatedAt())
          .build()
      )
      .toList();
    return authorDtos;
  }

  @Override
  public AuthorDto getAuthor(UUID id) {
    Author author = _findAuthorById(id);
    AuthorDto authorDto = AuthorDto
      .builder()
      .id(author.getId())
      .name(author.getName())
      .description(author.getDescription())
      .createdAt(author.getCreatedAt())
      .updatedAt(author.getUpdatedAt())
      .build();

    return authorDto;
  }

  @Override
  public AuthorDto createAuthorDto(CreateAuthorDto dto) {
    Author newAuthor = new Author();
    newAuthor.setName(dto.getName());
    newAuthor.setDescription(dto.getDescription());
    Author savedAuthor = _authorRepository.save(newAuthor);
    return AuthorDto
      .builder()
      .id(savedAuthor.getId())
      .name(savedAuthor.getName())
      .description(savedAuthor.getDescription())
      .createdAt(savedAuthor.getCreatedAt())
      .updatedAt(savedAuthor.getUpdatedAt())
      .build();
  }

  @Override
  public void deleteAuthor(UUID id) {
    Author author = _findAuthorById(id);
    _authorRepository.delete(author);
  }

  @Override
  public void updateAuthor(UpdateAuthorDto dto, UUID id) {
    Author found = _findAuthorById(id);

    if (Objects.nonNull(dto.getName())) {
      found.setName(dto.getName());
    }

    if (Objects.nonNull(dto.getDescription())) {
      found.setDescription(dto.getDescription());
    }

    _authorRepository.save(found);
  }

  private Author _findAuthorById(UUID id) {
    Optional<Author> result = _authorRepository.findById(id);

    if (result.isEmpty()) {
      throw new NotFoundException("Not found with this ID: " + id);
    }

    return result.get();
  }
}
