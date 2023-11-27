package com.example.springmodels.repositories;

import com.example.springmodels.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    List<BookModel> findByAuthor(String author);
}
