package hr.fer.rsikspr.chatbot.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ChatbotExceptions {

  public static class ConversationNotFoundException extends ChatbotException {

    public ConversationNotFoundException(String message) {
      super(message);
    }
  }

  public static class OpenAiWebClientException extends ChatbotException {

    public OpenAiWebClientException(String message) {
      super(message);
    }
  }

  public static class OpenAiServerException extends ChatbotException {

    public OpenAiServerException(String message) {
      super(message);
    }
  }

  public static class NotEnoughCreditException extends ChatbotException {

    public NotEnoughCreditException(String message) {
      super(message);
    }
  }
}
