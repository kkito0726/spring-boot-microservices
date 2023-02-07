package com.ken.authorapi;

import com.ken.shared.errors.RestResponseEntityExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ RestResponseEntityExceptionHandler.class })
public class AuthorApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorApiApplication.class, args);
  }
}
