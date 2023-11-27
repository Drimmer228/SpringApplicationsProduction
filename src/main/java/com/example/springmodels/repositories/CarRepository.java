package com.example.springmodels.repositories;

import com.example.springmodels.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findByMake(String make);
    
}
