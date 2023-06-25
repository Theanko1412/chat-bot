package hr.fer.rsikspr.chatbot.controller;

import hr.fer.rsikspr.chatbot.model.dto.ConversationDTO;
import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import hr.fer.rsikspr.chatbot.service.impl.ConversationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/conversations", produces = "application/json")
@Tag(name = "Conversations")
public class ConversationController {
  private final ConversationServiceImpl conversationServiceImpl;
  private final ModelMapper modelMapper;

  @GetMapping(value = "/{conversationId}")
  public ConversationDTO getConversation(@PathVariable String conversationId) {
    Conversation conversation = conversationServiceImpl.getConversationById(conversationId);

    return modelMapper.map(conversation, ConversationDTO.class);
  }

  @GetMapping(value = "/search")
  public ConversationDTO getConversationByParticipants(
      @RequestParam("participant1") String participant1,
      @RequestParam("participant2") String participant2) {

    Conversation conversation =
        conversationServiceImpl.getConversationByParticipants(participant1, participant2);

    return modelMapper.map(conversation, ConversationDTO.class);
  }

  @GetMapping(value = "/searchByDate")
  public List<ConversationDTO> getConversationsBetweenDates(
      @RequestParam("startDate") LocalDateTime startDate,
      @RequestParam("endDate") LocalDateTime endDate) {

    List<Conversation> conversations =
        conversationServiceImpl.getConversationsBetweenDates(startDate, endDate);

    return conversations.stream()
        .map(conversation -> modelMapper.map(conversation, ConversationDTO.class))
        .toList();
  }

  @PostMapping(value = "/{conversationId}/close", consumes = "application/json")
  public ConversationDTO closeConversation(@PathVariable String conversationId) {
    Conversation conversation = conversationServiceImpl.closeConversation(conversationId);

    return modelMapper.map(conversation, ConversationDTO.class);
  }
}
