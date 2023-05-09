package hr.fer.rsikspr.chatbot.exceptions;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConversationNotFoundException.class)
  public ResponseEntity<ApiError> handleConversationNotFoundException(
      ConversationNotFoundException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError =
        new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage(), path, LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiError);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgumentException(
      IllegalArgumentException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), path, LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
  }
}
