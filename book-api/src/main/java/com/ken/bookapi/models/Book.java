package com.ken.bookapi.models;

import com.ken.shared.models.EntityBase;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Book extends EntityBase {

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(
    name = "book_author",
    joinColumns = @JoinColumn(name = "book_id")
  )
  @Type(type = "uuid-char")
  @Column(name = "author_id")
  private List<UUID> authors;
}
