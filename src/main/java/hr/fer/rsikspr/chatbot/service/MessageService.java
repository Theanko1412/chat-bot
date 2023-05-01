package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.Conversation;
import hr.fer.rsikspr.chatbot.model.Message;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import hr.fer.rsikspr.chatbot.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository messageRepository;
  private final ConversationRepository conversationRepository;

  public Message addMessageToConversation(String conversationId, Message message) {
    Conversation conversation = conversationRepository.findConversationById(conversationId)
        .orElseThrow(() -> new EntityNotFoundException("Conversation with a given id doesnt exist."));

    message.setConversation(conversation);
    conversation.getMessages().add(message);

    messageRepository.save(message);
    conversationRepository.save(conversation);
    return message;
  }

  public Message addMessage(Message message) {
    Conversation conversation = conversationRepository.findConversationByInitialSenderAndInitialReceiver(message.getSender(), message.getReceiver())
        .orElseGet(() -> {
          Conversation conv = new Conversation(message.getSender(), message.getReceiver());
          return conversationRepository.save(conv);
        });

    message.setConversation(conversation);
    messageRepository.save(message);

    conversation.addMessage(message);
    conversationRepository.save(conversation);
    return message;
  }

}
