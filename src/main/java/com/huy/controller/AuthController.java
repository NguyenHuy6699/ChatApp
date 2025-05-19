package com.huy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huy.converter.UserDTOConverter;
import com.huy.dto.UserDTO;
import com.huy.entity.UserEntity;
import com.huy.model.Status;
import com.huy.service.UserService;

@RestController
@RequestMapping("/user")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public Status register(@RequestParam String userName, @RequestParam String password) {
		return userService.register(userName, password);
	}
	
	@PostMapping("/login") 
	public Object[] login(
			@RequestParam String userName,
			@RequestParam String password
			) {
		Status status = new Status();
		UserEntity existingUser = userService.findByUserName(userName);
		UserDTO userDto = null;
		if(existingUser != null && existingUser.getPassword().equals(password)) {
			status.setOk(true);
			status.setMessage("Đăng nhập thành công");
			userDto = UserDTOConverter.toDTO(existingUser);
		} else {
			status.setOk(false);
			status.setMessage("Sai tên tài khoản hoặc mật khẩu");
		}
		return new Object[] {status, userDto};
	}
	
	@GetMapping("/test")
	public Status test() {
		return new Status(true, "ok");
	}
}
