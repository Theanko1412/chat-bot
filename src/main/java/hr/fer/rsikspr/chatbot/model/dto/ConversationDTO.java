package hr.fer.rsikspr.chatbot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Schema(title = "Conversation", description = "Conversation DTO")
@Data
public class ConversationDTO {

  @Schema(
      title = "id",
      description = "Generated conversation uuid",
      example = "6f53add4-119b-45fc-bef3-d1011ba21aa9",
      accessMode = AccessMode.READ_ONLY)
  private String id;

  @Schema(
      title = "initialSender",
      description = "Conversation initial sender",
      example = "0012345678",
      accessMode = AccessMode.READ_ONLY)
  @NotNull private String initialSender;

  @Schema(
      title = "initialReceiver",
      description = "Conversation initial receiver",
      example = "0012345678",
      accessMode = AccessMode.READ_ONLY)
  @NotNull private String initialReceiver;

  @Schema(
      title = "createdAt",
      description = "Conversation start timestamp",
      example = "2021-05-01T10:00:00",
      accessMode = AccessMode.READ_ONLY)
  private LocalDateTime createdAt;

  @Schema(
      title = "closedAt",
      description = "Conversation end timestamp",
      example = "2021-05-01T10:00:00",
      accessMode = AccessMode.READ_ONLY)
  private LocalDateTime closedAt;

  @Schema(
      title = "messages",
      description = "Conversation messages",
      accessMode = AccessMode.READ_ONLY)
  private List<MessageDTO> messages;
}
