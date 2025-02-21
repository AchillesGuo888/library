package com.example.library.service.auth;

import com.example.library.entity.UserInfo;
import com.example.library.exception.BizException;
import lombok.Getter;

@Getter
public abstract class AbstractAuth {

  /**
   * current user
   */
  private UserInfo currentUser;

  public AbstractAuth(UserInfo tUser) {
    this.currentUser = tUser;
  }

  /**
   * auth
   *
   * @throws BizException
   */
  public abstract void auth() throws BizException;
}
