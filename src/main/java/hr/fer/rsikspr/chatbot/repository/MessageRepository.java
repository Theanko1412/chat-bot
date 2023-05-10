package hr.fer.rsikspr.chatbot.repository;

import hr.fer.rsikspr.chatbot.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
  // no use case for this

  //  Optional<List<Message>> findMessagesBySender(String sender);
  //
  //  Optional<List<Message>> findMessagesByReceiver(String receiver);
}
