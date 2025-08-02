package com.huy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huy.entity.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
	SessionEntity findByDeviceId(String deviceId);
}
