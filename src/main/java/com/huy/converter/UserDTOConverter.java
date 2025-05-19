package com.huy.converter;

import java.util.ArrayList;
import java.util.List;

import com.huy.dto.UserDTO;
import com.huy.entity.UserEntity;

public class UserDTOConverter {
	public static UserDTO toDTO (UserEntity userEntity) {
		UserDTO userDto = new UserDTO();
		userDto.setId(userEntity.getId());
		userDto.setUserName(userEntity.getUserName());
		userDto.setAvatarUrl(userEntity.getAvatarUrl());
		return userDto;
	}
	
	public static List<UserDTO> toListDTO(List<UserEntity> userEntityList) {
		List<UserDTO> userDTOs = new ArrayList<>();
		if (!userEntityList.isEmpty()) {
			for (UserEntity userEntity : userEntityList) {
				UserDTO userDto = new UserDTO();
				userDto.setId(userEntity.getId());
				userDto.setUserName(userEntity.getUserName());
				userDto.setAvatarUrl(userEntity.getAvatarUrl());
				userDTOs.add(userDto);
			}
		}
		return userDTOs;
	}
}