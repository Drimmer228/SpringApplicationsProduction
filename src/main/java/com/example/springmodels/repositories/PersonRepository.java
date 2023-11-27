package com.example.springmodels.repositories;

import com.example.springmodels.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonModel, Long> {
    List<PersonModel> findByName(String name);
    
}
