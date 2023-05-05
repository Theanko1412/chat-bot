package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;

public interface ConversationService {

  Conversation getConversationById(String conversationId);

  Conversation getConversationByParticipants(String sender, String receiver);

  Conversation closeConversation(String conversationId);

  Conversation createConversation(String sender, String receiver);

  Conversation updateConversationWithMessage(Conversation conversation, Message message);
}
