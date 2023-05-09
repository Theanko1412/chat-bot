package hr.fer.rsikspr.chatbot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.repository.ConversationRepository;
import hr.fer.rsikspr.chatbot.service.impl.ConversationServiceImpl;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {

  @InjectMocks
  @Spy
  private ConversationServiceImpl conversationServiceImpl;

  @Mock
  private ConversationRepository conversationRepository;

  @Test
  void testGetConversationById() {

    String conversationId = "34b2cfdc-f557-4722-8a5c-892332ace235";

    Conversation expectedConversation = new Conversation(
        conversationId, "test", "test", null, LocalDateTime.of(2025, 1, 1, 1, 1, 1), null);

    when(conversationRepository.findConversationById(conversationId))
        .thenReturn(Optional.of(expectedConversation));

    Conversation actualConversation = conversationServiceImpl.getConversationById(conversationId);

    verify(conversationRepository, times(1)).findConversationById(anyString());
    assertEquals(expectedConversation, actualConversation);
  }

  @Test
  void testGetConversationByIdError() {

    String conversationId = "random id";

    when(conversationRepository.findConversationById(conversationId))
        .thenThrow(new ConversationNotFoundException("Conversation with a given id doesnt exist."));

    assertThrows(
        ConversationNotFoundException.class,
        () -> conversationServiceImpl.getConversationById(conversationId));
  }

  @Test
  void testGetConversationByIdNull() {
    // does this make sense to even write?

    String conversationId = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.getConversationById(conversationId));
  }

  @Test
  void testGetConversationByIdBlank() {

    String conversationId = "";

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.getConversationById(conversationId));
  }

  @Test
  void testGetConversationByParticipantsCorrectOrder() {
    String sender = "sender";
    String receiver = "receiver";
    Conversation expectedConversation = new Conversation(
        "34b2cfdc-f557-4722-8a5c-892332ace235",
        sender,
        receiver,
        null,
        LocalDateTime.of(2025, 1, 1, 1, 1, 1),
        null);

    when(conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
            sender, receiver))
        .thenReturn(Optional.of(expectedConversation));

    Conversation actualConversation =
        conversationServiceImpl.getConversationByParticipants(sender, receiver);

    verify(conversationRepository, times(1))
        .findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(sender, receiver);
    assertEquals(expectedConversation, actualConversation);
  }

  @Test
  void testGetConversationByParticipantsWrongOrder() {
    String sender = "sender";
    String receiver = "receiver";
    Conversation expectedConversation = new Conversation(
        "34b2cfdc-f557-4722-8a5c-892332ace235",
        sender,
        receiver,
        null,
        LocalDateTime.of(2025, 1, 1, 1, 1, 1),
        null);

    when(conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
            sender, receiver))
        .thenReturn(Optional.empty());

    when(conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
            receiver, sender))
        .thenReturn(Optional.of(expectedConversation));

    Conversation actualConversation =
        conversationServiceImpl.getConversationByParticipants(sender, receiver);

    verify(conversationRepository, times(2))
        .findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(anyString(), anyString());
    assertEquals(expectedConversation, actualConversation);
  }

  @Test
  void testGetConversationByParticipantsNotFound() {
    String sender = "sender";
    String receiver = "receiver";

    when(conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
            sender, receiver))
        .thenReturn(Optional.empty());

    when(conversationRepository.findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
            receiver, sender))
        .thenReturn(Optional.empty());

    assertThrows(
        ConversationNotFoundException.class,
        () -> conversationServiceImpl.getConversationByParticipants(sender, receiver));

    verify(conversationRepository, times(2))
        .findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(anyString(), anyString());
  }

  @Test
  void testCloseConversation() {
    String conversationId = "34b2cfdc-f557-4722-8a5c-892332ace235";

    Conversation givenConversation = new Conversation(
        conversationId, "test", "test", null, LocalDateTime.of(2025, 1, 1, 1, 1, 1), null);

    doReturn(givenConversation).when(conversationServiceImpl).getConversationById(conversationId);

    // return what's being sent to save() without calling save
    when(conversationRepository.save(any(Conversation.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Conversation actualConversation = conversationServiceImpl.closeConversation(conversationId);

    verify(conversationRepository, times(1)).save(actualConversation);
    assertNotNull(actualConversation.getClosedAt());
  }

  @Test
  void testCloseConversationAlreadyClosed() {
    String conversationId = "34b2cfdc-f557-4722-8a5c-892332ace235";

    Conversation givenCnversation = new Conversation(
        conversationId,
        "test",
        "test",
        null,
        LocalDateTime.of(2025, 1, 1, 1, 1, 1),
        LocalDateTime.of(2025, 1, 1, 1, 1, 1));

    doReturn(givenCnversation).when(conversationServiceImpl).getConversationById(conversationId);

    assertThrows(
        ConversationNotFoundException.class,
        () -> conversationServiceImpl.closeConversation(conversationId));
  }

  @Test
  void testCloseConversationWithNullId() {
    String conversationId = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.closeConversation(conversationId));
  }

  @Test
  void testCreateConversation() {
    String sender = "sender";
    String receiver = "receiver";
    String conversationId = "34b2cfdc-f557-4722-8a5c-892332ace235";

    Conversation expectedConversation = new Conversation(
        conversationId, sender, receiver, null, LocalDateTime.of(2025, 1, 1, 1, 1, 1), null);

    when(conversationRepository.save(any(Conversation.class))).thenReturn(expectedConversation);

    Conversation actualConversation = conversationServiceImpl.createConversation(sender, receiver);

    assertEquals(expectedConversation, actualConversation);
  }

  @Test
  void testCreateConversationWithNullSender() {
    String sender = null;
    String receiver = "receiver";

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.createConversation(sender, receiver));
  }

  @Test
  void testCreateConversationWithNullReceiver() {
    String sender = "sender";
    String receiver = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.createConversation(sender, receiver));
  }

  @Test
  void testUpdateConversationWithMessage() {
    String conversationId = "34b2cfdc-f557-4722-8a5c-892332ace235";
    String sender = "sender";
    String receiver = "receiver";

    Conversation givenConversation = new Conversation(
        conversationId,
        sender,
        receiver,
        new LinkedList<>(),
        LocalDateTime.of(2025, 1, 1, 1, 1, 1),
        null);

    Message givenMessage = new Message(
        "messageId",
        sender,
        receiver,
        "message",
        LocalDateTime.of(2025, 1, 1, 1, 1, 1),
        givenConversation);

    // return what's being sent to save() without calling save
    when(conversationRepository.save(any(Conversation.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Conversation actualConversation =
        conversationServiceImpl.updateConversationWithMessage(givenConversation, givenMessage);

    verify(conversationRepository, times(1)).save(actualConversation);
    assertTrue(actualConversation.getMessages().contains(givenMessage));
  }

  @Test
  void testUpdateConversationWithNullConversation() {
    Conversation conversation = null;
    Message message = new Message(
        "messageId", "sender", "receiver", "message", LocalDateTime.of(2025, 1, 1, 1, 1, 1), null);

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.updateConversationWithMessage(conversation, message));
  }

  @Test
  void testUpdateConversationWithNullMessage() {
    Conversation conversation = new Conversation(
        "34b2cfdc-f557-4722-8a5c-892332ace235",
        "sender",
        "receiver",
        new LinkedList<>(),
        LocalDateTime.of(2025, 1, 1, 1, 1, 1),
        null);
    Message message = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> conversationServiceImpl.updateConversationWithMessage(conversation, message));
  }
}
