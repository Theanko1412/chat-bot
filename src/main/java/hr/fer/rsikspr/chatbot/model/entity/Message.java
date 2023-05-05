package hr.fer.rsikspr.chatbot.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "message_id")
  private String id;

  @NotNull @Column(name = "sender")
  private String sender;

  @NotNull @Column(name = "receiver")
  private String receiver;

  @NotNull @Column(name = "content")
  private String content;

  @Column(name = "timestamp")
  private LocalDateTime timestamp;

  @ManyToOne
  @JoinColumn(name = "conversation_id", nullable = false)
  private Conversation conversation;

  @Builder
  public Message(@NonNull String sender, @NonNull String receiver, @NonNull String content) {
    this.sender = sender;
    this.receiver = receiver;
    this.content = content;
    this.timestamp = LocalDateTime.now();
  }

  public Message() {
    this.timestamp = LocalDateTime.now();
  }
}
