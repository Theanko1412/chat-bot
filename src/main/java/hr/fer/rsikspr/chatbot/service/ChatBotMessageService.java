package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.dto.MessageDTO;
import hr.fer.rsikspr.chatbot.model.entity.Message;

public interface ChatBotMessageService {

  MessageDTO generateOpenAIResponse(Message message);
}
