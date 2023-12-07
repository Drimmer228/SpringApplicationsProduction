package com.example.springmodels.repositories;

import com.example.springmodels.models.SalesStatisticsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesStatisticsRepository extends CrudRepository<SalesStatisticsModel,Long> {
    List<SalesStatisticsModel> findAllByApplicationId(Long application_id);
}
