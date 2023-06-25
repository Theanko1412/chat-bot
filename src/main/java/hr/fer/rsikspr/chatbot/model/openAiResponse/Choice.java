package hr.fer.rsikspr.chatbot.model.openAiResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "Choice", description = "Open AI response data - choice")
@Data
public class Choice {

  private String text;
  private int index;
  private Object logprobs;
  private String finish_reason;
}
