package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.Conversation;
import hr.fer.rsikspr.chatbot.model.Message;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationService {

  private final ConversationRepository conversationRepository;

  public Conversation getConversationById(String conversationId) {
    return conversationRepository.findConversationById(conversationId)
        .orElseThrow(() -> new EntityNotFoundException("Conversation with a given id doesnt exist."));
  }

  public List<Message> getMessagesFromConversation(String conversationId) {
    return conversationRepository.findConversationById(conversationId)
        .orElseThrow(() -> new EntityNotFoundException("Conversation with a given id doesnt exist."))
        .getMessages();
  }

  public Conversation getConversation(String sender, String receiver) {
    return conversationRepository.findConversationByInitialSenderAndInitialReceiver(sender, receiver)
        .orElseThrow(() -> new EntityNotFoundException("Conversation with a given id doesnt exist."));
  }

  public Conversation createConversation(Conversation conversation) {
    return conversationRepository.save(conversation);
  }
}
