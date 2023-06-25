package hr.fer.rsikspr.chatbot.controller;

import hr.fer.rsikspr.chatbot.model.dto.MessageDTO;
import hr.fer.rsikspr.chatbot.model.entity.Message;
import hr.fer.rsikspr.chatbot.service.impl.ChatBotMessageServiceImpl;
import hr.fer.rsikspr.chatbot.service.impl.MessageServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/chatbot", produces = "application/json")
@Tag(name = "Chat bot")
public class ChatBotController {

  private final MessageServiceImpl messageServiceImpl;
  private final ChatBotMessageServiceImpl messageResponseServiceImpl;
  private final ModelMapper modelMapper;

  @PostMapping(consumes = "application/json")
  public ResponseEntity<MessageDTO> generateResponse(@Valid @RequestBody MessageDTO messageDTO) {

    if (messageDTO.getReceiver() == null) {
      messageDTO.setReceiver("ChatBot");
    } else {
      throw new IllegalArgumentException(
          "Receiver must not be specified on chatbot path, use /messages for normal messaging");
    }

    Message newMessage = modelMapper.map(messageDTO, Message.class);
    newMessage = messageServiceImpl.publishMessage(newMessage);

    MessageDTO chatBotResponse = messageResponseServiceImpl.generateOpenAIResponse(newMessage);
    Message chatBotResponseMessage = modelMapper.map(chatBotResponse, Message.class);
    chatBotResponseMessage = messageServiceImpl.publishMessage(chatBotResponseMessage);

    return ResponseEntity.created(
            URI.create("/messages/" + chatBotResponseMessage.getConversation().getId()))
        .body(modelMapper.map(chatBotResponseMessage, MessageDTO.class));
  }
}
