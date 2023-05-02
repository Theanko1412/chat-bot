package hr.fer.rsikspr.chatbot.controller;

import hr.fer.rsikspr.chatbot.model.Message;
import hr.fer.rsikspr.chatbot.model.dto.MessageDTO;
import hr.fer.rsikspr.chatbot.service.MessageService;
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
@RequestMapping("/messages")
public class MessageController {

  private final MessageService messageService;
  private final ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<MessageDTO> addMessageToConversation(
      @Valid @RequestBody MessageDTO messageDTO) {
    Message newMessage = modelMapper.map(messageDTO, Message.class);
    newMessage = messageService.addMessage(newMessage);

    return ResponseEntity.created(
            URI.create("/messages/" + newMessage.getConversation().getId()))
        .body(modelMapper.map(newMessage, MessageDTO.class));
  }
}
