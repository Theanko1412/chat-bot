package hr.fer.rsikspr.chatbot.service;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import org.junit.jupiter.api.Test;

public class MessageServiceTest {

  @Test
  void testPublishMessage() {

    Message message = Message.builder()
        .sender("sender")
        .receiver("receiver")
        .content("content")
        .build();

    Conversation conversation = Conversation.builder()
        .initialSender("sender")
        .initialReceiver("receiver")
        .build();
  }
}
