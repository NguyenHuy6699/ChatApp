package com.huy.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huy.converter.UserDTOConverter;
import com.huy.dto.UserDTO;
import com.huy.dto.output.Contact;
import com.huy.entity.UserEntity;
import com.huy.firebase.message.FcmSender;
import com.huy.helper.UserHelper;
import com.huy.model.Status;
import com.huy.repository.UserRepository;
import com.huy.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void saveUser(UserEntity user) {
		userRepository.save(user);
	}

	@Override
	public UserEntity findByUserName(String userName) {
		UserEntity userEntity = userRepository.findOneByUserName(userName);
		return userEntity;
	}

	@Override
	public List<UserDTO> findAllContacts(String userName) {
		UserEntity userEntity = userRepository.findOneByUserName(userName);
		List<Long> friendIds = userEntity.getFriendIds();
		List<UserDTO> friends = new ArrayList();
		for (Long id : friendIds) {
			Optional<UserEntity> friend = userRepository.findById(id);
			if (friend != null) {
				UserDTO contact = UserDTOConverter.toDTO(friend.get());
				friends.add(contact);
			}
		}
		return friends;
	}

	@Override
	public Status addFriend(Long userId, Long friendId) {
		Status status = new Status(false, "Thêm bạn thất bại");
		Optional<UserEntity> user = userRepository.findById(userId);
		Optional<UserEntity> friend = userRepository.findById(friendId);
		if(user.get() != null && friend.get() != null) {
			UserEntity userEntity = user.get();
			UserEntity friendEntity = friend.get();
			Status status1 = userEntity.addFriend(friendEntity);
			Status status2 = friendEntity.addFriend(userEntity);
			userRepository.save(userEntity);
			userRepository.save(friendEntity);
			if (status1.isOk() && status2.isOk()) {
				status.setOk(true);
			} else {
				status.setOk(false);
			}
			status.setMessage(status1.getMessage());
		} else {
			status.setOk(false);
			status.setMessage("User không tồn tại");
		}
		
		return status;
	}

	@Override
	public Status register(String userName, String password) {
		Status status = new Status();
		UserEntity existUser = userRepository.findOneByUserName(userName);
		if (existUser != null) {
			status.setOk(false);
			status.setMessage("Người dùng đã tồn tại");
			return status;
		}
		UserEntity newUser = new UserEntity();
		newUser.setUserName(userName);
		newUser.setPassword(password);
		newUser.setAvatarUrl(null);
		userRepository.save(newUser);
		UserEntity newUser2 = userRepository.findOneByUserName(newUser.getUserName());
		if (newUser2 != null) {
			status.setOk(true);
			status.setMessage("Đăng ký thành công");
		}
		return status;
	}

	@Override
	public List<UserDTO> findUserBy(String query) {
		List<UserEntity> userList = userRepository.findByUserNameContainingIgnoreCase(query);
		List<UserDTO> userDtoList = UserDTOConverter.toListDTO(userList);
		return userDtoList;
	}

	@Override
	public String sendNotiMessage(String title, String message, String senderUserName, String receiverUserName) throws Exception {
		userRepository.findOneByUserName(receiverUserName);
		
		return FcmSender.sendMessage(title, message, senderUserName, senderUserName);
	}
}
