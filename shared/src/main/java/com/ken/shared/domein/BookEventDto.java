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
public class BookEventDto extends DtoBase {

  private String title;
  private String description;
  private List<UUID> authors;
}
