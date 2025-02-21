package com.example.library.service.impl.user;


import cn.hutool.core.util.StrUtil;
import com.example.library.common.base.ResponseCode;
import com.example.library.common.constant.CommonConstant;
import com.example.library.dto.SessionUser;
import com.example.library.dto.request.RegisterRequestDTO;
import com.example.library.dto.response.RegisterResponse;
import com.example.library.entity.UserInfo;
import com.example.library.entity.UserInfoExample;
import com.example.library.exception.BizException;
import com.example.library.exception.NoRollbackException;
import com.example.library.mapper.UserInfoMapper;
import com.example.library.service.user.UserRegisterService;
import com.example.library.util.JwtUtil;
import com.example.library.util.Md5Util;
import com.example.library.util.ValidateUtils;
import com.google.common.base.Strings;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@AllArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

  private final UserInfoMapper userInfoMapper;


  @Override
  public RegisterResponse register(RegisterRequestDTO requestDTO,
      HttpServletRequest httpServletRequest) throws BizException {
    String code = requestDTO.getCode();
    String email = requestDTO.getEmail();
    String password = requestDTO.getPassword();

    /**
     * 1.check parameters
     */
    //check: parameters formation
    checkParameters(code, email, password);
    //check: whether the email exists or not
    isUserExistByEmail(email);

    UserInfo newUser = insertNewUser(requestDTO);
    /**
     * 3.get token after sign-up
     */
    String token = JwtUtil
        .generateToken(newUser.getUserId().toString(), requestDTO.getUserType());
    //create session
    HttpSession session = httpServletRequest.getSession();
    SessionUser sessionUser = new SessionUser();
    BeanUtils.copyProperties(newUser, sessionUser);
    session.setAttribute("user", sessionUser); // put user info into session
    session.setMaxInactiveInterval(1800);
    return RegisterResponse.builder().accessToken(token).build();
  }



  //check input parameters
  private void checkParameters(String code, String email, String password) throws BizException {
    if (Strings.isNullOrEmpty(email) || !email.contains(CommonConstant.EMAIL)) {
      throw new BizException(ResponseCode.email_error_rules);
    }
    if (StrUtil.isBlank(code)) {
      throw new BizException(ResponseCode.code_false);
    }
    if (StrUtil.isBlank(password) || !ValidateUtils.checkPassword(password)) {
      throw new BizException(ResponseCode.password_error_rules);
    }
  }

  //check whether the email has exist or not
  private void isUserExistByEmail(String email) throws BizException {
    UserInfoExample example = new UserInfoExample();
    UserInfoExample.Criteria criteria = example.createCriteria();
    criteria.andEmailEqualTo(email);

    List<UserInfo> userList = userInfoMapper.selectByExample(example);
    if (CollectionUtils.isNotEmpty(userList)) {
      throw new BizException(ResponseCode.email_has_exist);
    }
  }

  //create the new user
  @Transactional(rollbackFor = Exception.class, noRollbackFor = NoRollbackException.class)
  public UserInfo insertNewUser(RegisterRequestDTO requestDTO) {

    /**
     * 2.create new user
     */
    // create Salt
    String salt = Md5Util.createSalt();

    // create user
    UserInfo newUser = new UserInfo();
    BeanUtils.copyProperties(requestDTO, newUser);
    // store salt secret in DB
    String passwordInDb = Md5Util.getSaltMd5AndSha(requestDTO.getPassword(), salt);
    Long newUserId = Md5Util.createNewUserId();
    newUser.setUserId(newUserId.toString());
    newUser.setPassword(passwordInDb);
    newUser.setSalt(salt);
    userInfoMapper.insertSelective(newUser);

    return newUser;
  }
}
