package com.ken.shared.domein;

import com.ken.shared.models.DtoBase;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AuthorEventDto extends DtoBase {

  private String name;
  private String description;
  private List<UUID> Books;
}
