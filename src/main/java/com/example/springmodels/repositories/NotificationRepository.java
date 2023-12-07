package com.example.springmodels.repositories;

import com.example.springmodels.models.NotificationsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationsModel, Long> {
    Iterable<NotificationsModel> findAllByUserId(Long user_id);
    void deleteAllByUserId(Long user_id);
}
