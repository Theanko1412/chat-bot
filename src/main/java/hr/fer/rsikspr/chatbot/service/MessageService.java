package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;

public interface MessageService {

  Message publishMessage(Message message);

  Message updateMessageWithConversation(Message message, Conversation conversation);
}
