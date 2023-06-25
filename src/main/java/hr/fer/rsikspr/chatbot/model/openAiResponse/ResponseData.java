package hr.fer.rsikspr.chatbot.model.openAiResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Schema(title = "Response data", description = "Open AI response data")
@Data
public class ResponseData {
  private String id;
  private String object;
  private long created;
  private String model;
  private List<Choice> choices;
  private Usage usage;
}
