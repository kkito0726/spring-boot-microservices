package com.ken.authorapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorDto {

  @Length(max = 50)
  private String name;

  @Length(max = 255)
  private String description;
}
