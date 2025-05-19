package com.huy.helper;

import com.huy.dto.UserDTO;
import com.huy.dto.output.Contact;
import com.huy.entity.UserEntity;

public class UserHelper {
	
	public static UserEntity toUserEntity(UserDTO userDto) {
		UserEntity entity = new UserEntity();
		return entity;
	}
	
	public static UserDTO toUserDTO(UserEntity userEntity) {
		UserDTO dto = new UserDTO();
		dto.setId(userEntity.getId());
		dto.setUserName(userEntity.getUserName());
		return dto;
	}
	
	public static Contact convertToContact(UserEntity userEntity) {
		Contact contact = new Contact();
		contact.setId(userEntity.getId());
		contact.setName(userEntity.getUserName());
		return contact;
	}
}
