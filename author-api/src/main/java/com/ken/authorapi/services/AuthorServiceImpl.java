package com.ken.authorapi.services;

import com.ken.authorapi.dtos.AuthorDto;
import com.ken.authorapi.dtos.CreateAuthorDto;
import com.ken.authorapi.dtos.UpdateAuthorDto;
import com.ken.authorapi.mappers.AuthorMapper;
import com.ken.authorapi.models.Author;
import com.ken.authorapi.repositories.AuthorRepository;
import com.ken.shared.constants.RabbitMQKeys;
import com.ken.shared.domein.AuthorEventDto;
import com.ken.shared.errors.NotFoundException;
import com.ken.shared.models.CustomMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository _authorRepository;
  private final AuthorMapper _authorMapper;
  private final RabbitTemplate _template;

  @Override
  public List<AuthorDto> getAuthors() {
    List<Author> authors = _authorRepository.findAll();
    List<AuthorDto> authorDtos = authors
      .stream()
      .map(authorItem -> _authorMapper.toDto(authorItem))
      .toList();
    return authorDtos;
  }

  @Override
  public AuthorDto getAuthor(UUID id) {
    Author author = _findAuthorById(id);
    AuthorDto authorDto = _authorMapper.toDto(author);

    return authorDto;
  }

  @Override
  public AuthorDto createAuthorDto(CreateAuthorDto dto) {
    Author newAuthor = new Author(dto.getName(), dto.getDescription());
    Author savedAuthor = _authorRepository.save(newAuthor);

    CustomMessage<AuthorEventDto> message = new CustomMessage<>();
    message.setMessageDate(LocalDateTime.now());
    message.setMessageId(UUID.randomUUID());
    message.setPayload(_authorMapper.toEventDto(savedAuthor));

    _template.convertAndSend(
      RabbitMQKeys.AUTHOR_CREATED_EXCHANGE,
      null,
      message
    );

    return _authorMapper.toDto(savedAuthor);
  }

  @Override
  public void deleteAuthor(UUID id) {
    Author author = _findAuthorById(id);
    _authorRepository.delete(author);

    CustomMessage<AuthorEventDto> message = new CustomMessage<>();
    message.setMessageDate(LocalDateTime.now());
    message.setMessageId(UUID.randomUUID());
    message.setPayload(_authorMapper.toEventDto(author));

    _template.convertAndSend(
      RabbitMQKeys.AUTHOR_DELETED_EXCHANGE,
      null,
      message
    );
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

    CustomMessage<AuthorEventDto> message = new CustomMessage<>();
    message.setMessageDate(LocalDateTime.now());
    message.setMessageId(UUID.randomUUID());
    message.setPayload(_authorMapper.toEventDto(found));

    _template.convertAndSend(
      RabbitMQKeys.AUTHOR_UPDATE_EXCHANGE,
      null,
      message
    );
  }

  private Author _findAuthorById(UUID id) {
    Optional<Author> result = _authorRepository.findById(id);

    if (result.isEmpty()) {
      throw new NotFoundException("Not found with this ID: " + id);
    }

    return result.get();
  }
}
