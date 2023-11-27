package com.example.springmodels.repositories;

import com.example.springmodels.models.BookDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDetailRepository extends JpaRepository<BookDetailModel, Long> {
    List<BookDetailModel> findAll();
}
