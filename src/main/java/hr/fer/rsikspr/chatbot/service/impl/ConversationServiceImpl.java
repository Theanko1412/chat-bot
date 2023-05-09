package hr.fer.rsikspr.chatbot.service.impl;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import hr.fer.rsikspr.chatbot.service.ConversationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

  private final ConversationRepository conversationRepository;

  public Conversation getConversationById(String conversationId) {
    if (conversationId == null || conversationId.isBlank())
      throw new IllegalArgumentException("Given conversation id is null.");

    return conversationRepository
        .findConversationById(conversationId)
        .orElseThrow(
            () -> new ConversationNotFoundException("Conversation with a given id doesnt exist."));
  }

  public Conversation getConversationByParticipants(String sender, String receiver) {
    if (sender == null || sender.isBlank() || receiver == null || receiver.isBlank())
      throw new IllegalArgumentException("Both sender and receiver must be given.");

    return conversationRepository
        .findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(sender, receiver)
        .or(() ->
            conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
                receiver, sender))
        .orElseThrow(() -> new ConversationNotFoundException("Conversation doesnt exist."));
  }

  @Override
  public Conversation closeConversation(String conversationId) {
    if (conversationId == null || conversationId.isBlank())
      throw new IllegalArgumentException("Given conversation id is null.");

    Conversation conversation = getConversationById(conversationId);

    if (conversation.getClosedAt() == null) {
      conversation.setClosedAt(LocalDateTime.now());
    } else {
      // conv is closed/archived user doesn't know it exist
      throw new ConversationNotFoundException("Conversation doesnt exist.");
    }
    return conversationRepository.save(conversation);
  }

  @Override
  public Conversation createConversation(String sender, String receiver) {
    if (sender == null || sender.isBlank() || receiver == null || receiver.isBlank())
      throw new IllegalArgumentException("Both sender and receiver must be given.");

    Conversation conversation =
        Conversation.builder().initialSender(sender).initialReceiver(receiver).build();

    return conversationRepository.save(conversation);
  }

  @Override
  public Conversation updateConversationWithMessage(Conversation conversation, Message message) {
    if (conversation == null)
      throw new IllegalArgumentException("Given conversation must not be null.");
    if (message == null) throw new IllegalArgumentException("Given message must not be null.");

    conversation.addMessageToList(message);
    return conversationRepository.save(conversation);
  }
}
