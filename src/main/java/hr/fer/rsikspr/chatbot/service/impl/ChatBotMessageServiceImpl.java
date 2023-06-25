package hr.fer.rsikspr.chatbot.service.impl;

import hr.fer.rsikspr.chatbot.exceptions.ChatbotExceptions.*;
import hr.fer.rsikspr.chatbot.model.dto.MessageDTO;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.model.openAiResponse.ResponseData;
import hr.fer.rsikspr.chatbot.service.ChatBotMessageService;
import io.netty.channel.ChannelOption;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@RequiredArgsConstructor
public class ChatBotMessageServiceImpl implements ChatBotMessageService {

  private WebClient webClient() {

    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000)
        .responseTimeout(Duration.ofMillis(20000));

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl("https://api.openai.com/v1/engines/text-curie-001/completions")
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + System.getenv("OPENAI_API_KEY"))
        .build();
  }

  @Override
  public MessageDTO generateOpenAIResponse(Message message) {

    WebClient webClient = webClient();

    // TODO move to configuration
    String jsonBody = String.format(
        """
            {
              "prompt": "%s",
              "max_tokens": 100,
              "n": 1,
              "stop": null,
              "temperature": 1.0,
              "top_p": 1.0,
              "presence_penalty": 0.0,
              "frequency_penalty": 0.0,
              "best_of": 1,
              "echo": false
            }
        """,
        message.getContent());

    WebClient.RequestHeadersSpec<?> requestSpec = webClient
        .post()
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(jsonBody);

    Mono<ResponseData> responseMono = requestSpec.exchangeToMono(response -> {
      if (response.statusCode().is4xxClientError()) {
        return response.bodyToMono(String.class).flatMap(errorBody -> {
          System.out.println("Client Error: " + response.statusCode() + " - " + errorBody);
          return Mono.error(new OpenAiWebClientException("HTTP error: " + response.statusCode()));
        });
      } else if (response.statusCode().is5xxServerError()) {
        return response.bodyToMono(String.class).flatMap(errorBody -> {
          System.out.println("Server Error: " + response.statusCode() + " - " + errorBody);
          return Mono.error(new OpenAiServerException("HTTP error: " + response.statusCode()));
        });
      } else {
        return response.bodyToMono(ResponseData.class);
      }
    });

    return responseMono
        .flatMap(responseData -> {
          if (responseData.getChoices() != null && !responseData.getChoices().isEmpty()) {
            return Mono.just(new MessageDTO(
                message.getReceiver(),
                message.getSender(),
                responseData.getChoices().get(0).getText().strip()));
          } else {
            return Mono.empty();
          }
        })
        .onErrorResume(OpenAiWebClientException.class, e -> {
          System.out.println("Handling ClientErrorException: " + e.getMessage());
          return Mono.just(new MessageDTO(
              message.getReceiver(),
              message.getSender(),
              "Looks like you are out of API calls. Come back later."));
        })
        .onErrorResume(OpenAiServerException.class, e -> {
          System.out.println("Handling ServerErrorException: " + e.getMessage());
          return Mono.just(
              new MessageDTO(
                  message.getReceiver(),
                  message.getSender(),
                  "Sorry I didn't understand that. Looks like OpenAI is having some issues. Come back later."));
        })
        .block(Duration.ofSeconds(20));
  }
}
