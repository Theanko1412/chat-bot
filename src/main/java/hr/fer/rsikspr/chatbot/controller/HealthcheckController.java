package hr.fer.rsikspr.chatbot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
@Tag(name = "Healthcheck")
public class HealthcheckController {

  @GetMapping(value = "/health")
  public String performHealthcheck() {
    return "OK";
  }
}
