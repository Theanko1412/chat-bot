package hr.fer.rsikspr.chatbot.model.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ConversationDTO {

  private String id;

  @NotNull private String initialSender;

  @NotNull private String initialReceiver;

  private LocalDateTime createdAt;
  private LocalDateTime closedAt;
  private List<MessageDTO> messages;
}
