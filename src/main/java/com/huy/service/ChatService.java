package com.huy.service;

import java.util.List;

import com.huy.dto.ChatMessageDTO;
import com.huy.entity.ChatMessage;

public interface ChatService {
	void save(ChatMessage message);
	List<ChatMessageDTO> getChatHistory(String userA, String userB);
}
