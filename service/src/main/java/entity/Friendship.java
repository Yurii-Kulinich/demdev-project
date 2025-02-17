package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {

  @Id
  private UUID id;
  @Column(name = "user1_id")
  private UUID user1Id;
  @Column(name = "user2_id")
  private UUID user2Id;
  private Status status;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

}
