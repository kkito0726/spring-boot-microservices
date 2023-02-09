package com.ken.bookapi.dtos;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDto {

  @NotNull
  @Length(max = 50)
  private String title;

  @NotNull
  @Length(max = 255)
  private String description;

  private List<UUID> authors;
}
