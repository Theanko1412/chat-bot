package hr.fer.rsikspr.chatbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.repository.MessageRepository;
import hr.fer.rsikspr.chatbot.service.impl.ConversationServiceImpl;
import hr.fer.rsikspr.chatbot.service.impl.MessageServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

  @InjectMocks
  @Spy
  private MessageServiceImpl messageServiceImpl;

  @Mock
  private MessageRepository messageRepository;

  @Mock
  private ConversationServiceImpl conversationServiceImpl;

  @Test
  void testUpdateMessageWithConversation() {

    Message message = new Message("sender", "receiver", "content");

    Conversation conversation =
        new Conversation("id", "sender", "receiver", null, LocalDateTime.now(), null);

    when(messageRepository.save(any(Message.class))).thenReturn(message);

    Message actualMessage = messageServiceImpl.updateMessageWithConversation(message, conversation);

    assertEquals(actualMessage.getConversation(), conversation);
  }

  @Test
  void testUpdateMessageWithConversationNullConversation() {

    Message message = new Message("sender", "receiver", "content");

    Conversation conversation = null;

    when(messageRepository.save(any(Message.class))).thenReturn(message);

    assertThrows(IllegalArgumentException.class, () -> {
      messageServiceImpl.updateMessageWithConversation(message, conversation);
    });
  }

  @Test
  void testUpdateMessageWithConversationNullMessage() {

    Message message = null;

    Conversation conversation =
        new Conversation("id", "sender", "receiver", null, LocalDateTime.now(), null);

    assertThrows(IllegalArgumentException.class, () -> {
      messageServiceImpl.updateMessageWithConversation(message, conversation);
    });
  }

  @Test
  void testUpdateMessageWithConversationWhenMessageHasConversation() {

    Conversation conversation =
        new Conversation("id", "sender", "receiver", null, LocalDateTime.now(), null);

    Message message =
        new Message("id", "sender", "receiver", "content", LocalDateTime.now(), conversation);

    conversation.setMessages(List.of(message));

    assertThrows(IllegalArgumentException.class, () -> {
      messageServiceImpl.updateMessageWithConversation(message, conversation);
    });
  }

  @Test
  void testPublishMessage() {
    Message message = new Message("id", "sender", "receiver", "content", LocalDateTime.now(), null);

    Conversation conversation =
        new Conversation("id", "sender", "receiver", null, LocalDateTime.now(), null);

    when(conversationServiceImpl.getConversationByParticipants(
            any(String.class), any(String.class)))
        .thenReturn(conversation);
    when(messageServiceImpl.updateMessageWithConversation(message, conversation))
        .thenReturn(message);
    when(conversationServiceImpl.updateConversationWithMessage(conversation, message))
        .thenReturn(conversation);

    Message actualMessage = messageServiceImpl.publishMessage(message);

    verify(conversationServiceImpl, times(1))
        .getConversationByParticipants(any(String.class), any(String.class));
    verify(messageServiceImpl, times(1))
        .updateMessageWithConversation(any(Message.class), any(Conversation.class));
    verify(conversationServiceImpl, times(1))
        .updateConversationWithMessage(any(Conversation.class), any(Message.class));
  }

  @Test
  void testPublishMessageWhenConversationDoesNotExist() {
    Message message = new Message("id", "sender", "receiver", "content", LocalDateTime.now(), null);

    Conversation conversation =
        new Conversation("id", "sender", "receiver", null, LocalDateTime.now(), null);

    when(conversationServiceImpl.getConversationByParticipants(
            any(String.class), any(String.class)))
        .thenThrow(ConversationNotFoundException.class);
    when(conversationServiceImpl.createConversation(any(String.class), any(String.class)))
        .thenReturn(conversation);
    when(messageServiceImpl.updateMessageWithConversation(message, conversation))
        .thenReturn(message);
    when(conversationServiceImpl.updateConversationWithMessage(conversation, message))
        .thenReturn(conversation);

    Message actualMessage = messageServiceImpl.publishMessage(message);

    verify(conversationServiceImpl, times(1))
        .createConversation(any(String.class), any(String.class));
    verify(messageServiceImpl, times(1))
        .updateMessageWithConversation(any(Message.class), any(Conversation.class));
    verify(conversationServiceImpl, times(1))
        .updateConversationWithMessage(any(Conversation.class), any(Message.class));
  }
}
