package hr.fer.rsikspr.chatbot.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(title = "ApiError", description = "Api error response")
public class ApiError {

  @Schema(title = "status code", description = "Error status code", example = "400")
  private int statusCode;

  @Schema(title = "status", description = "Error status", example = "Bad request")
  private String status;

  @Schema(title = "message", description = "Error description", example = "Invalid request body")
  private String message;

  @Schema(title = "path", description = "Request path", example = "/messages")
  private String path;

  @Schema(title = "timestamp", description = "Error timestamp", example = "2021-05-01T10:00:00")
  private LocalDateTime timestamp;

  public ApiError(int statusCode, String status, String message, String path) {
    this.statusCode = statusCode;
    this.status = status;
    this.message = message;
    this.path = path;
    this.timestamp = LocalDateTime.now();
  }
}
