package com.example.springmodels.repositories;

import com.example.springmodels.models.HouseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<HouseModel, Long> {
    List<HouseModel> findByAddress(String address);
    
}
