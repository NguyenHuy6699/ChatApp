package com.huy.websocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.dto.ChatMessageDTO;
import com.huy.entity.ChatMessage;
import com.huy.entity.UserEntity;
import com.huy.repository.ChatRepository;
import com.huy.repository.UserRepository;
import com.huy.service.ChatService;

@Component
public class MySocketHandler extends TextWebSocketHandler {
	private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;
	
	private final ChatRepository chatRepo;
	private final UserRepository userRepo;
	
	@Autowired
	public MySocketHandler (ChatRepository chatRepo, UserRepository userRepo, ObjectMapper objectMapper) {
		this.chatRepo = chatRepo;
		this.userRepo = userRepo;
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String query = session.getUri().getQuery();
		String userName = getUserName(session);
		sessions.put(userName, session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		String receiverUserName = "";
		ChatMessageDTO chatMessageDto = objectMapper.readValue(payload, ChatMessageDTO.class);
		if (chatMessageDto != null) {
			UserEntity sender = userRepo.findOneByUserName(chatMessageDto.getSenderUserName());
			UserEntity receiver = userRepo.findOneByUserName(chatMessageDto.getReceiverUserName());
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setSender(sender);
			chatMessage.setReceiver(receiver);
			chatMessage.setMessage(chatMessageDto.getMessage());
			chatMessage.setTimestamp(chatMessageDto.getTimestamp());
			chatRepo.save(chatMessage);
			
			receiverUserName = chatMessageDto.getReceiverUserName();
			WebSocketSession receiverSession = sessions.get(receiverUserName);
			if (receiverSession != null && receiverSession.isOpen()) {
				String value = objectMapper.writeValueAsString(chatMessageDto);
				receiverSession.sendMessage(new TextMessage(value));
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		sessions.remove(getUserName(session));
	}
	
	private String getUserName(WebSocketSession session) {
		String query = session.getUri().getQuery();
		return query != null && query.startsWith("userName=") ? query.split("=")[1] : session.getId();
	}
}
