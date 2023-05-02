package hr.fer.rsikspr.chatbot.model.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

@Data
public class MessageDTO {

  private String id;

  @NotNull private String sender;

  @NotNull private String receiver;

  @NotNull private String content;

  private String conversationId;
  private LocalDateTime timestamp;

  public MessageDTO(@NonNull String sender, @NonNull String receiver, @NonNull String content) {
    this.sender = sender;
    this.receiver = receiver;
    this.content = content;
    this.timestamp = LocalDateTime.now();
  }

  public MessageDTO() {
    this.timestamp = LocalDateTime.now();
  }
}
