package com.cityStar.rowmapper;

import com.cityStar.dto.NotificationDTO;
import com.cityStar.model.Notification;
import com.cityStar.model.User;

public class NotificationRowMapper {
    public static Notification toEntity(NotificationDTO dto,User user) {
        return Notification.builder()
                .isRead(false)
                .message(dto.getMessage())
                .createdAt(dto.getCreatedAt())
                .user(user)
                .build();
    }
}
