package com.huy.dto;

public class UserDTO extends BaseDTO {
	private String userName;
	private String avatarUrl;
	private String defaultAvatarUrl;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getDefaultAvatarUrl() {
		return defaultAvatarUrl;
	}
	public void setDefaultAvatarUrl(String defaultAvatarUrl) {
		this.defaultAvatarUrl = defaultAvatarUrl;
	}
}
