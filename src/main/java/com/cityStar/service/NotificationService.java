package com.cityStar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cityStar.model.Notification;
import com.cityStar.model.User;
import com.cityStar.repository.InotificationRepository;

@Service
public class NotificationService {

    private final InotificationRepository notificationRepository;
    public NotificationService(InotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public void createNotification(User user, String message) {
        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
    }

    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}
