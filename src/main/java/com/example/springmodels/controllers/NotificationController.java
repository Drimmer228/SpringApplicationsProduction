package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.models.NotificationsModel;
import com.example.springmodels.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final ApiInterface apiInterface;

    @Autowired
    public NotificationController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @GetMapping
    public Iterable<NotificationsModel> getNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = apiInterface.getUserAtUserName(auth.getName());
        List<NotificationsModel> notificationsModels = apiInterface.findAllByUserId(userModel.getId());
        return notificationsModels;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> markNotificationAsRead(@PathVariable Long id) {
        Optional<NotificationsModel> notificationOptional = apiInterface.findNotifyById(id);
        if (notificationOptional.isPresent()) {
            NotificationsModel notification = notificationOptional.get();
            notification.setRead(true);
            apiInterface.saveNotification(notification);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Transactional
    @PostMapping("/clear")
    public void clearNotificationsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = apiInterface.getUserAtUserName(auth.getName());

        if (userModel != null) {
            apiInterface.deleteAllNotifyByUserId(userModel.getId());
        }
    }
}

