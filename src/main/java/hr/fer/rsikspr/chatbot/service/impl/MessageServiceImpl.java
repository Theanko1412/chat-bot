package hr.fer.rsikspr.chatbot.service.impl;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.repository.MessageRepository;
import hr.fer.rsikspr.chatbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;

  private final ConversationServiceImpl conversationServiceImpl;

  // adding message to conversation, if conversation doesn't exist, it will be created
  // conversation is same for 2 participants no matter who is sender and who is receiver
  public Message publishMessage(Message message) {

    Conversation conversation;
    try {
      conversation = conversationServiceImpl.getConversationByParticipants(
          message.getSender(), message.getReceiver());
    } catch (ConversationNotFoundException e) {
      conversation =
          conversationServiceImpl.createConversation(message.getSender(), message.getReceiver());
    }

    updateMessageWithConversation(message, conversation);

    conversationServiceImpl.updateConversationWithMessage(conversation, message);

    return message;
  }

  @Override
  public Message updateMessageWithConversation(Message message, Conversation conversation) {
    message.setConversation(conversation);
    return messageRepository.save(message);
  }
}
