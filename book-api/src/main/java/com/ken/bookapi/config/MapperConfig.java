package com.ken.bookapi.config;

import com.ken.bookapi.dtos.BookDto;
import com.ken.bookapi.models.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean
  ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper
      .typeMap(Book.class, BookDto.class)
      .addMappings(mapper ->
        mapper.map(src -> src.getAuthors(), BookDto::setAuthorIds)
      );

    modelMapper.addMappings(
      new PropertyMap<Book, BookDto>() {
        @Override
        protected void configure() {
          skip(destination.getAuthors());
        }
      }
    );
    return modelMapper;
  }
}
