package hr.fer.rsikspr.chatbot.exceptions;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Conversation not found")
  @ExceptionHandler(ConversationNotFoundException.class)
  public ResponseEntity<ApiError> handleConversationNotFoundException(
      ConversationNotFoundException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), path);
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiError);
  }

  @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad request")
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgumentException(
      IllegalArgumentException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_GATEWAY.getReasonPhrase(),
        e.getMessage(),
        path);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
  }

  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        e.getMessage(),
        path);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiError);
  }
}
