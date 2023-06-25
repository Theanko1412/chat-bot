package hr.fer.rsikspr.chatbot.repository;

import hr.fer.rsikspr.chatbot.model.entity.Conversation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

  Optional<Conversation> findConversationById(String id);

  Optional<Conversation> findConversationByIdAndClosedAtNull(String id);

  Optional<List<Conversation>> findConversationsByCreatedAtBetween(
      LocalDateTime startDate, LocalDateTime endDate);

  // TODO: move to custom query
  Optional<Conversation> findConversationByInitialSenderAndInitialReceiverAndClosedAtNull(
      String initialSender, String initialReceiver);
}
