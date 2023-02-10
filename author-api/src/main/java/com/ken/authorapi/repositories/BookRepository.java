package com.ken.authorapi.repositories;

import com.ken.authorapi.models.Book;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {}
