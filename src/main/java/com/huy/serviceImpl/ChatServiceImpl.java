package com.huy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huy.converter.ChatMessageDTOConverter;
import com.huy.dto.ChatMessageDTO;
import com.huy.entity.ChatMessage;
import com.huy.repository.ChatRepository;
import com.huy.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatRepository chatRepo;
	
	@Override
	public List<ChatMessageDTO> getChatHistory(String userA, String userB) {
		List<ChatMessage> chatMessageList = chatRepo.getChatHistory(userA, userB);
		List<ChatMessageDTO> chatMessageDTOList = new ArrayList();
		for (ChatMessage chatMessage : chatMessageList) {
			chatMessageDTOList.add(ChatMessageDTOConverter.toDTO(chatMessage));
		}
		return chatMessageDTOList;
	}	
}
