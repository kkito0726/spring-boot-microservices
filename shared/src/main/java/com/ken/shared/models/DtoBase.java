package com.ken.shared.models;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DtoBase {

  private UUID id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
