package hr.fer.rsikspr.chatbot.exceptions;

import java.time.LocalDateTime;

public record ApiError(int status, String errorMessage, String path, LocalDateTime timestamp) {}
