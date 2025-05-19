package com.huy.entity;
import java.util.ArrayList;
import java.util.List;

import com.huy.constant.TableName;
import com.huy.model.Status;

import jakarta.persistence.*;

@Entity
@Table(name = TableName.user)
public class UserEntity extends BaseEntity {
	private String userName;
	private String password;
	private String avatarUrl;
	private String fcmToken;
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private List<ChatMessage> sentMessages = new ArrayList();
	
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<ChatMessage> receivedMessages = new ArrayList();
	
	private List<Long> friendIds = new ArrayList();
	public List<Long> getFriendIds() {
		return friendIds;
	}
	public void setFriends(List<Long> friends) {
		this.friendIds = friends;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	
	public UserEntity(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public UserEntity() {
		super();
	}
	public Status addFriend(UserEntity friend) {
		Status status = new Status();
		if (!this.friendIds.contains(friend.getId())) {
			this.friendIds.add(friend.getId());
			status.setOk(true);
			status.setMessage("Thêm bạn thành công");
		} else {
			status.setOk(false);
			status.setMessage("Đã là bạn bè");
		}
		return status;
	}
}
