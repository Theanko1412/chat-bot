package hr.fer.rsikspr.chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

  @GetMapping("/health")
  public String performHealthcheck() {
    return "OK";
  }
}
