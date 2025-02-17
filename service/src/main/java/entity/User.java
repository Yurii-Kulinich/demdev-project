package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "users")
public class User {

  @Id
  private UUID id;
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  private byte[] profilePicture;
  private LocalDate birthDate;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

}
