package hr.fer.rsikspr.chatbot.controller;

import hr.fer.rsikspr.chatbot.model.dto.ConversationDTO;
import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.service.impl.ConversationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/conversations")
@Tag(name = "Conversations")
public class ConversationController {
  private final ConversationServiceImpl conversationServiceImpl;
  private final ModelMapper modelMapper;

  @GetMapping("/{conversationId}")
  public ConversationDTO getConversation(@PathVariable String conversationId) {
    Conversation conversation = conversationServiceImpl.getConversationById(conversationId);

    return modelMapper.map(conversation, ConversationDTO.class);
  }

  @GetMapping("/search")
  public ConversationDTO getConversationByParticipants(
      @RequestParam("sender") String sender, @RequestParam("receiver") String receiver) {

    Conversation conversation =
        conversationServiceImpl.getConversationByParticipants(sender, receiver);

    return modelMapper.map(conversation, ConversationDTO.class);
  }

  @PatchMapping("/{conversationId}")
  public ConversationDTO closeConversation(@PathVariable String conversationId) {
    Conversation conversation = conversationServiceImpl.closeConversation(conversationId);

    return modelMapper.map(conversation, ConversationDTO.class);
  }
}
