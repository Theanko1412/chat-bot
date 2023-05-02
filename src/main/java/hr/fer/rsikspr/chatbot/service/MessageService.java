package hr.fer.rsikspr.chatbot.service;

import hr.fer.rsikspr.chatbot.model.Conversation;
import hr.fer.rsikspr.chatbot.model.Message;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import hr.fer.rsikspr.chatbot.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository messageRepository;
  private final ConversationRepository conversationRepository;

  // adding message to conversation, if conversation doesn't exist, it will be created
  // conversation is same for 2 participants no matter who is sender and who is receiver
  public Message addMessage(Message message) {
    Conversation conversation = conversationRepository
        // TODO: move to custom query
        .findConversationByInitialSenderAndInitialReceiver(
            message.getSender(), message.getReceiver())
        .orElseGet(() -> conversationRepository
            .findConversationByInitialSenderAndInitialReceiver(
                message.getReceiver(), message.getSender())
            .orElseGet(() -> {
              Conversation conv = new Conversation(message.getSender(), message.getReceiver());
              return conversationRepository.save(conv);
            }));

    message.setConversation(conversation);
    messageRepository.save(message);

    conversation.addMessageToList(message);
    conversationRepository.save(conversation);

    return message;
  }
}
