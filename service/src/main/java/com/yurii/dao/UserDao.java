package com.yurii.dao;

import static com.yurii.entity.QFriendship.friendship;
import static com.yurii.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQuery;
import com.yurii.entity.Post_;
import com.yurii.entity.User;
import com.yurii.entity.User_;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor
public class UserDao {

  private static final UserDao INSTANCE = new UserDao();

  public static UserDao getInstance() {
    return INSTANCE;
  }

  /**
   * Find all users having name and more than 1 friend
   */
  public List<User> findAllByFirstNameAndFriendsNumber(Session session, UserFilter filter) {
    var predicate = QPredicate.builder()
        .add(filter.getEmail(), user.email::eq)
        .add(filter.getFriendsNumber(), user.initiatedFriendships.size()::gt)
        .buildAnd();

    return new JPAQuery<User>(session)
        .select(user)
        .from(user)
        .join(user.initiatedFriendships, friendship)
        .where(predicate)
        .fetch();
  }

  public Double findAveragePostLengthByEmailAndFriendsNumber(Session session,
      UserFilter filter) {
    var cb = session.getCriteriaBuilder();
    var criteria = cb.createQuery(Double.class);

    var user = criteria.from(User.class);
    var post = user.join(User_.posts);

    List<Predicate> predicates = new ArrayList<>();
    if (filter.getEmail() != null) {
      predicates.add(cb.equal(user.get(User_.email), filter.getEmail()));
    }
    if (filter.getFriendsNumber() != null) {
      predicates.add(
          cb.gt(cb.size(user.get(User_.initiatedFriendships)), filter.getFriendsNumber()));
    }

    criteria.select(cb.avg(cb.length(post.get(Post_.text))))
        .where(predicates.toArray(Predicate[]::new));

    return session.createQuery(criteria).uniqueResult();
  }

}
