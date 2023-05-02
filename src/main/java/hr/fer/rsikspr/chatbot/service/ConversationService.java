package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.Conversation;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class ConversationService {

  private final ConversationRepository conversationRepository;

  public Conversation getConversationById(@NotBlank String conversationId) {
    return conversationRepository
        .findConversationById(conversationId)
        .orElseThrow(
            () -> new EntityNotFoundException("Conversation with a given id doesnt exist."));
  }

  public Conversation getConversationByParticipants(
      @NotBlank String sender, @NotBlank String receiver) {
    return conversationRepository
        .findConversationByInitialSenderAndInitialReceiver(sender, receiver)
        .orElseGet(() -> conversationRepository
            .findConversationByInitialSenderAndInitialReceiver(receiver, sender)
            .orElseThrow(() -> new EntityNotFoundException(String.format(
                "Conversation with a pair %s and %s doesn't exist.", sender, receiver))));
  }
}
