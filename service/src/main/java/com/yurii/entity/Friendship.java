package com.yurii.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "friend_id")
  private User friend;

  @Enumerated(EnumType.STRING)
  private Status status;
  private Instant createdAt;
  private Instant updatedAt;

  public void setUser(User user) {
    this.user = user;
    user.getInitiatedFriendships().add(this);
  }

  public void setFriend(User friend) {
    this.friend = friend;
    friend.getFriendshipsAsFriend().add(this);
  }
}
