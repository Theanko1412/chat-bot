package hr.fer.rsikspr.chatbot.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table(name = "conversation")
public class Conversation {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "conversation_id")
  private String id;

  @NonNull
  @Column(name = "initialSender")
  private String initialSender;
  @NonNull
  @Column(name = "initialReceiver")
  private String initialReceiver;

  @OneToMany(mappedBy = "conversation", cascade = {CascadeType.REMOVE})
  @Column(name = "messages")
  @JsonIgnore
  private List<Message> messages;

  @NonNull
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "closed_at")
  private LocalDateTime closedAt;

  public Conversation(@NonNull String initialSender, @NonNull String initialReceiver) {
    this.initialSender = initialSender;
    this.initialReceiver = initialReceiver;
    this.messages = new LinkedList<>();
    this.createdAt = LocalDateTime.now();
  }

  public Conversation() {
    createdAt = LocalDateTime.now();
  }

  public void addMessage(Message message) {
    messages.add(message);
  }
}
