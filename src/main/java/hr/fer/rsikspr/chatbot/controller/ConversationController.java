package hr.fer.rsikspr.chatbot.controller;

import hr.fer.rsikspr.chatbot.model.Conversation;
import hr.fer.rsikspr.chatbot.model.dto.ConversationDTO;
import hr.fer.rsikspr.chatbot.service.ConversationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/conversations")
@Tag(name = "Conversation")
public class ConversationController {
  private final ConversationService conversationService;
  private final ModelMapper modelMapper;

  @GetMapping("/{conversationId}")
  public ConversationDTO getConversation(@PathVariable String conversationId) {
    Conversation conversation = conversationService.getConversationById(conversationId);

    return modelMapper.map(conversation, ConversationDTO.class);
  }

  @GetMapping("/search")
  public ConversationDTO getConversationByParticipants(
      @RequestParam("sender") String sender, @RequestParam("receiver") String receiver) {

    Conversation conversation = conversationService.getConversationByParticipants(sender, receiver);

    return modelMapper.map(conversation, ConversationDTO.class);
  }
}
