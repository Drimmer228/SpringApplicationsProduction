package com.example.springmodels.repositories;

import com.example.springmodels.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByName(String name);
    
}
