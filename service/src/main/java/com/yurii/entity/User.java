package com.yurii.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "email", callSuper = false)
@ToString(exclude = {"initiatedFriendships", "friendshipsAsFriend", "posts", "comments", "likes"})
@Table(name = "users")
public class User extends AuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String password;
  private String firstName;
  private String lastName;
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
  private List<Friendship> initiatedFriendships = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "friend", cascade = CascadeType.PERSIST)
  private List<Friendship> friendshipsAsFriend = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Post> posts = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Comment> comments = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Like> likes = new ArrayList<>();

  private LocalDate birthDate;

  public void addPost(Post post) {
    posts.add(post);
    post.setUser(this);
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setUser(this);
  }

  public void addInitiatedFriendship(Friendship friendship) {
    initiatedFriendships.add(friendship);
    friendship.setUser(this);
  }

  public void addFriendshipAsFriend(Friendship friendship) {
    friendshipsAsFriend.add(friendship);
    friendship.setFriend(this);
  }

  public void addLike(Like like) {
    likes.add(like);
    like.setUser(this);
  }

}
