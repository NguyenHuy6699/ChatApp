package com.huy.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.huy.converter.UserDTOConverter;
import com.huy.dto.UserDTO;
import com.huy.entity.UserEntity;
import com.huy.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@PostMapping("/uploadAvatar")
	public ResponseEntity<String> uploadAvatar(
			@RequestParam String userName, 
			@RequestParam("file") MultipartFile file
			) throws IOException {
		UserEntity user = userService.findByUserName(userName);
		if (user == null) return ResponseEntity.notFound().build();
		
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path filePath = Paths.get(uploadPath, fileName);
		Files.createDirectories(filePath.getParent());
		Files.write(filePath, file.getBytes());
		user.setAvatarUrl("/uploads/" + fileName);
		userService.saveUser(user);
		return ResponseEntity.ok(user.getAvatarUrl());
	}
}
