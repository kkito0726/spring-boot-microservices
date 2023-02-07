package com.ken.shared.errors;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private List<String> messages;
  private HttpStatus httpStatus;
}
