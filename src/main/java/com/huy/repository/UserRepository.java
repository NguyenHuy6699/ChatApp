package com.huy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huy.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findOneByUserName(String userName); 
	void deleteByUserName(String userName);
	List<UserEntity> findByUserNameContainingIgnoreCase(String query);
}
