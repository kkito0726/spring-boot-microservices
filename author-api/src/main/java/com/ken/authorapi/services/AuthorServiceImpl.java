package com.ken.authorapi.services;

import com.ken.authorapi.dtos.AuthorDto;
import com.ken.authorapi.dtos.CreateAuthorDto;
import com.ken.authorapi.dtos.UpdateAuthorDto;
import com.ken.authorapi.models.Author;
import com.ken.authorapi.repositories.AuthorRepository;
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
        new AuthorDto(
          authorItem.getId(),
          authorItem.getName(),
          authorItem.getDescription()
        )
      )
      .toList();
    return authorDtos;
  }

  @Override
  public AuthorDto getAuthor(UUID id) {
    Author author = _findAuthorById(id);
    AuthorDto authorDto = new AuthorDto(
      author.getId(),
      author.getName(),
      author.getDescription()
    );
    return authorDto;
  }

  @Override
  public AuthorDto creAuthorDto(CreateAuthorDto dto) {
    Author newAuthor = new Author();
    newAuthor.setName(dto.getName());
    newAuthor.setDescription(dto.getDescription());
    Author savedAuthor = _authorRepository.save(newAuthor);
    return new AuthorDto(
      savedAuthor.getId(),
      savedAuthor.getName(),
      savedAuthor.getDescription()
    );
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
      throw new RuntimeException("Not found with this ID: " + id);
    }

    return result.get();
  }
}
