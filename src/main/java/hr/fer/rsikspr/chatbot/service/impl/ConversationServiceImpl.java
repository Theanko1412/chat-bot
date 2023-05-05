package hr.fer.rsikspr.chatbot.service.impl;

import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import hr.fer.rsikspr.chatbot.service.ConversationService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

  private final ConversationRepository conversationRepository;

  public Conversation getConversationById(String conversationId) {
    return conversationRepository
        .findConversationById(conversationId)
        .orElseThrow(
            () -> new EntityNotFoundException("Conversation with a given id doesnt exist."));
  }

  public Conversation getConversationByParticipants(String sender, String receiver) {
    return conversationRepository
        .findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(sender, receiver)
        .or(() ->
            conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
                receiver, sender))
        .orElse(null);
  }

  @Override
  public Conversation closeConversation(String conversationId) {
    Conversation conversation = getConversationById(conversationId);

    if (conversation.getClosedAt() == null) {
      conversation.setClosedAt(LocalDateTime.now());
    } else {
      // conv is closed/archived user doesn't know it exist
      throw new IllegalArgumentException("Conversation doesnt exist.");
    }
    return conversationRepository.save(conversation);
  }

  @Override
  public Conversation createConversation(String sender, String receiver) {
    Conversation conversation =
        Conversation.builder().initialSender(sender).initialReceiver(receiver).build();
    return conversationRepository.save(conversation);
  }

  @Override
  public Conversation updateConversationWithMessage(Conversation conversation, Message message) {
    conversation.addMessageToList(message);
    return conversationRepository.save(conversation);
  }
}
