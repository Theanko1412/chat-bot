package hr.fer.rsikspr.chatbot.model.openAiResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "Usage", description = "Open AI response data - usage")
@Data
public class Usage {

  private int prompt_tokens;
  private int completion_tokens;
  private int total_tokens;
}
