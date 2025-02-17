package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "post")
public class Post {
  @Id
  private UUID id;
  private UUID userId;
  private String title;
  private String text;
  private byte[] postPicture;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

}
