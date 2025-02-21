package com.example.library.util;

import com.example.library.common.base.ResponseCode;
import com.example.library.entity.UserInfo;
import com.example.library.entity.UserInfoExample;
import com.example.library.exception.BizException;
import com.example.library.mapper.UserInfoMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtil {

  private final UserInfoMapper userInfoMapper;

  /**
   * get user info by userId
   *
   * @param userId
   * @return
   */
  public UserInfo findUserByUserId(String userId) throws BizException {
    UserInfoExample example = new UserInfoExample();
    UserInfoExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userId);
    List<UserInfo> userList = userInfoMapper.selectByExample(example);
    if (CollectionUtils.isEmpty(userList)) {
      throw new BizException(ResponseCode.email_not_exist);
    }
    return userList.get(0);
  }

  /**
   * get user info by email
   *
   * @param email
   * @return
   */
  public UserInfo findUserByEmail(String email) throws BizException {
    UserInfoExample example = new UserInfoExample();
    UserInfoExample.Criteria criteria = example.createCriteria();
    criteria.andEmailEqualTo(email);
    List<UserInfo> userList = userInfoMapper.selectByExample(example);
    if (CollectionUtils.isEmpty(userList)) {
      throw new BizException(ResponseCode.email_not_exist);
    }
    return userList.get(0);
  }

}
