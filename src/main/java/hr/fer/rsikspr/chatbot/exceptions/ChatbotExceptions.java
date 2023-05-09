package hr.fer.rsikspr.chatbot.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ChatbotExceptions {

  public static class ConversationNotFoundException extends ChatbotException {

    public ConversationNotFoundException(String message) {
      super(message);
    }
  }
}
