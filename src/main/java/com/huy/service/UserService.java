package com.huy.service;

import java.util.List;

import com.huy.dto.UserDTO;
import com.huy.dto.output.Contact;
import com.huy.entity.UserEntity;
import com.huy.model.Status;

public interface UserService {
	void saveUser(UserEntity userDto);
	UserEntity findByUserName(String userName);
	List<UserDTO> findAllContacts(String userName);
	Status addFriend(Long userId, Long friendIds);
	Status register(String userName, String password);
	List<UserDTO> findUserBy(String query);
	String sendNotiMessage(String title, String message, String senderUserName, String receiverUserName) throws Exception; 
}
