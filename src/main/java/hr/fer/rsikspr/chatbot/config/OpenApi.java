package hr.fer.rsikspr.chatbot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi {

  @Value("${spring.application.name}")
  String name;

  @Value("${spring.application.version:1.0.0}")
  String version;

  @Value("${server.port}")
  String port;

  @Value("${server.servlet.context-path}")
  String contextPath;

  @Value("${API_CONTACT_EMAIL:'env not set'}")
  String contactEmail;

  @Bean
  public OpenAPI customOpenAPI() {

    Contact contact = contactEmail != null && contactEmail.contains("@")
        ? new Contact().name("Danko Curlin").email(contactEmail)
        : new Contact().name("Danko Curlin");

    return new OpenAPI()
        .info(new Info()
            .title(name)
            .description("Chatbot API for the RSiKSPR course")
            .version(version)
            .contact(contact))
        .servers(Collections.singletonList(
            new Server().description("Local server").url("http://localhost:" + port + contextPath)));
  }
}
