package hr.fer.rsikspr.chatbot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
@Tag(name = "Healthcheck")
public class HealthcheckController {

  private final String instanceId;

  // each application instance will have a unique id
  public HealthcheckController() {
    this.instanceId = UUID.randomUUID().toString();
  }

  @GetMapping(value = "/health")
  public String performHealthcheck() {
    return "Instance ID: " + instanceId + " is OK";
  }
}
