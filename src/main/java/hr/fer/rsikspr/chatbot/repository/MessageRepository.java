package hr.fer.rsikspr.chatbot.repository;

import hr.fer.rsikspr.chatbot.model.Message;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

  Optional<List<Message>> findMessagesBySender(String sender);

  Optional<List<Message>> findMessagesByReceiver(String receiver);
}
