package com.yurii.dao;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

  String email;
  Integer friendsNumber;

}
