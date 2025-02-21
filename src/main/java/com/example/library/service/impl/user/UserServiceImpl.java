package com.example.library.service.impl.user;


import com.example.library.common.base.ResponseCode;
import com.example.library.dto.SessionUser;
import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;
import com.example.library.dto.response.UserInfoResponse;
import com.example.library.entity.UserInfo;
import com.example.library.exception.BizException;
import com.example.library.service.auth.AbstractAuth;
import com.example.library.service.auth.Auth4EmailPasswordMatch;
import com.example.library.service.user.UserService;

import com.example.library.util.JwtUtil;
import com.example.library.util.UserUtil;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final JwtUtil jwtUtil;
  private final UserUtil userUtil;

  @Override
  public RegisterResponse userLogin(UserLoginRequestDTO requestDTO,
      HttpServletRequest httpServletRequest) throws BizException {

    //get auth tool
    AbstractAuth auth = getAuth(requestDTO);
    //auth user info
    auth.auth();
    //create token and return result
    String token = JwtUtil
        .generateToken(auth.getCurrentUser().getUserId(), auth.getCurrentUser().getUserType());
    //create session

    if (auth.getCurrentUser() != null) {
      HttpSession session = httpServletRequest.getSession();
      SessionUser sessionUser = new SessionUser();
      BeanUtils.copyProperties(auth.getCurrentUser(), sessionUser);
      session.setAttribute("user", sessionUser); // put user info into session
      session.setMaxInactiveInterval(1800);
    }

    return RegisterResponse.builder().accessToken(token).build();
  }

  private AbstractAuth getAuth(UserLoginRequestDTO requestDTO) throws BizException {
    UserInfo user = null;
    AbstractAuth auth = null;
    //email and password
    user = userUtil.findUserByEmail(requestDTO.getEmail());
    String salt = user.getSalt();
    return new Auth4EmailPasswordMatch(user, requestDTO.getPassword(), salt);

  }



  @Override
  public void userLogout(String token, HttpServletRequest httpServletRequest) {
    if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
      String userId = jwtUtil.getUserIdFromToken(token);
      jwtUtil.blacklistToken(token.substring(7)); // put Token into blacklist
      SecurityContextHolder.clearContext();
      //destroy session
      httpServletRequest.getSession().invalidate();
    }
  }

  //search user info
  @Override
  public UserInfoResponse getUserInfo(String token) throws BizException {
    String userId = jwtUtil.getUserIdFromToken(token);
    if (StringUtils.isEmpty(userId)) {
      throw new BizException(ResponseCode.token_error);
    }

    UserInfo user = userUtil.findUserByUserId(userId);

    UserInfoResponse response = UserInfoResponse.builder().build();
    BeanUtils.copyProperties(user, response);
    return response;
  }




}
