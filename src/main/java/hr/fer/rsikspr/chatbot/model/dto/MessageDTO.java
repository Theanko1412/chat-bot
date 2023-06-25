package hr.fer.rsikspr.chatbot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

@Schema(title = "Message", description = "Message DTO")
@Data
public class MessageDTO {

  @Schema(
      title = "id",
      description = "Generated message uuid",
      example = "465ca95a-8308-4587-8d6e-8f8a0adead38",
      accessMode = AccessMode.READ_ONLY)
  private String id;

  @Schema(
      title = "sender",
      description = "Message sender",
      example = "0012345678",
      requiredMode = RequiredMode.REQUIRED)
  @NotNull private String sender;

  @Schema(
      title = "receiver",
      description = "Message receiver",
      example = "0012345678",
      requiredMode = RequiredMode.NOT_REQUIRED)
  private String receiver;

  @Schema(
      title = "content",
      description = "Message content",
      example = "Hello, how are you?",
      requiredMode = RequiredMode.REQUIRED)
  @NotNull private String content;

  @Schema(
      title = "conversationId",
      description = "Conversation id",
      example = "465ca95a-8308-4587-8d6e-8f8a0adead38",
      accessMode = AccessMode.READ_ONLY)
  private String conversationId;

  @Schema(
      title = "timestamp",
      description = "Message timestamp",
      example = "2021-05-01T10:00:00",
      accessMode = AccessMode.READ_ONLY)
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
