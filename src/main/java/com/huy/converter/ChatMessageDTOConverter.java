package com.huy.converter;

import com.huy.dto.ChatMessageDTO;
import com.huy.entity.ChatMessage;

public class ChatMessageDTOConverter {
	public static ChatMessageDTO toDTO (ChatMessage chatMessage) {
		ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
 		chatMessageDTO.setMessage(chatMessage.getMessage());
		chatMessageDTO.setReceiverUserName(chatMessage.getReceiver().getUserName());
		chatMessageDTO.setSenderUserName(chatMessage.getSender().getUserName());
		chatMessageDTO.setTimestamp(chatMessage.getTimestamp());
		
		return chatMessageDTO;
	}
}
