package com.app.chat.repository;

public interface ChatMessageReadRepository {

	public void markAsRead(Long chatMessageId, String userId);
	
	public boolean existsRead(Long chatMessageId, String userId);
}
