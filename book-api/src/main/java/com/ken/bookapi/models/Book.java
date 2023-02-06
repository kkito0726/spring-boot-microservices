package com.ken.bookapi.models;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

  @Id
  @Column(name = "id")
  @Type(type = "uuid-char")
  private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @PrePersist
  protected void PrePersist() {
    if (Objects.isNull(this.id)) {
      this.id = UUID.randomUUID();
    }
  }
}