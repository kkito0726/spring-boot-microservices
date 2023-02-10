package com.ken.authorapi.dtos;

import com.ken.shared.models.DtoBase;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto extends DtoBase {

  private String name;
  private String description;
  private List<BookDto> books;
  private List<UUID> bookIds;
}
