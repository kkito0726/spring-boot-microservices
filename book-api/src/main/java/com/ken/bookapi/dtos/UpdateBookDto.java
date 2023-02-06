package com.ken.bookapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDto {

  @Length(max = 50)
  private String title;

  @Length(max = 255)
  private String description;
}
