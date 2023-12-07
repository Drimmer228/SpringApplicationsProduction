package com.example.springmodels.repositories;

import com.example.springmodels.models.AppCategoryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCategoryRepository extends CrudRepository<AppCategoryModel, Long> {
}
