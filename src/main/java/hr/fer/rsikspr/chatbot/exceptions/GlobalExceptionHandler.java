package hr.fer.rsikspr.chatbot.exceptions;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.ConversationNotFoundException;
import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.NotEnoughCreditException;
import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.OpenAiServerException;
import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.OpenAiWebClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotEnoughCreditException.class)
  public ResponseEntity<ApiError> handleNotEnoughCreditException(
      NotEnoughCreditException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.PAYMENT_REQUIRED.value(),
        HttpStatus.PAYMENT_REQUIRED.getReasonPhrase(),
        e.getMessage(),
        path);
    return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED.value()).body(apiError);
  }

  @ExceptionHandler(OpenAiServerException.class)
  public ResponseEntity<ApiError> handleOpenAiWebClientException(
      OpenAiServerException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        e.getMessage(),
        path);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiError);
  }

  @ExceptionHandler(OpenAiWebClientException.class)
  public ResponseEntity<ApiError> handleOpenAiWebClientException(
      OpenAiWebClientException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        e.getMessage(),
        path);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiError);
  }

  @ExceptionHandler(ConversationNotFoundException.class)
  public ResponseEntity<ApiError> handleConversationNotFoundException(
      ConversationNotFoundException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), path);
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiError);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgumentException(
      IllegalArgumentException e, HttpServletRequest request) {
    String path = request.getContextPath() + request.getServletPath();

    ApiError apiError = new ApiError(
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        e.getMessage(),
        path);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
  }

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
