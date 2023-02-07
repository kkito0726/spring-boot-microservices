package com.ken.bookapi;

import com.ken.shared.errors.RestResponseEntityExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ RestResponseEntityExceptionHandler.class })
public class BookApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookApiApplication.class, args);
  }
}
