package com.huy.service;

import java.util.List;

import com.huy.dto.ChatMessageDTO;

public interface ChatService {
	List<ChatMessageDTO> getChatHistory(String userA, String userB);
}
