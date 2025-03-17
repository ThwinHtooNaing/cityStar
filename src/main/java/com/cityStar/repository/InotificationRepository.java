package com.cityStar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Notification;

public interface InotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserId(Long userId);
}
