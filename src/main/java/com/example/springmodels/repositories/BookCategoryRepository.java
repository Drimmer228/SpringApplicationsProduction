package com.example.springmodels.repositories;

import com.example.springmodels.models.BookCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategoryModel, Long> {
    List<BookCategoryModel> findAll();
}
