package com.example.springmodels.repositories;

import com.example.springmodels.models.ApplicationStatusModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends CrudRepository<ApplicationStatusModel, Long> {
    ApplicationStatusModel findApplicationStatusModelByName(String name);
}
