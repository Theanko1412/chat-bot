package hr.fer.rsikspr.chatbot.repository;

import hr.fer.rsikspr.chatbot.model.Conversation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

  Optional<Conversation> findConversationById(String id);
  Optional<Conversation> findConversationByInitialSenderAndInitialReceiver(String initialSender, String initialReceiver);

}