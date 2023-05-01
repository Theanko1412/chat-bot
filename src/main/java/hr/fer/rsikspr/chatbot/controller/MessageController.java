package hr.fer.rsikspr.chatbot.controller;

import hr.fer.rsikspr.chatbot.model.Conversation;
import hr.fer.rsikspr.chatbot.model.Message;
import hr.fer.rsikspr.chatbot.service.ConversationService;
import hr.fer.rsikspr.chatbot.service.MessageService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

  private final ConversationService conversationService;
  private final MessageService messageService;

  @GetMapping("/{conversationId}")
  public List<Message> getMessagesFromConversation(@PathVariable String conversationId) {
    return conversationService.getMessagesFromConversation(conversationId);
  }

  @PostMapping("/")
  public ResponseEntity<Message> addMessageToConversation(@RequestBody Message message) {
    messageService.addMessage(message);
    return ResponseEntity.created(URI.create("/messages/" + message.getConversation().getId()))
        .body(message);
  }

}
