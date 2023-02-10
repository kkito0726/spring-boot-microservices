package com.ken.authorapi.dtos;

import java.util.List;
import java.util.UUID;
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

  private List<UUID> books;
}
