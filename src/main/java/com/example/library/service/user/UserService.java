package com.example.library.service.user;


import com.example.library.dto.request.ForgetPasswordRequestDTO;
import com.example.library.dto.request.ModifyUserInfoRequestDTO;
import com.example.library.dto.request.PasswordModifyRequestDTO;
import com.example.library.dto.request.RegisterRequestDTO;
import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;

import com.example.library.dto.response.UpdateInfoResponse;
import com.example.library.dto.response.UserInfoResponse;
import com.example.library.exception.BizException;
import javax.servlet.http.HttpServletRequest;


public interface UserService {

  /**
   * user login interface
   *
   * @param requestDTO
   * @param httpServletRequest
   * @return
   */
  RegisterResponse userLogin(UserLoginRequestDTO requestDTO,
      HttpServletRequest httpServletRequest) throws BizException;


  /**
   * user logout
   * @param token
   * @param httpServletRequest
   */
  void userLogout(String token, HttpServletRequest httpServletRequest);

  /**
   * query user info by token(userId)
   * @param token
   * @return
   * @throws BizException
   */
  UserInfoResponse getUserInfo(String token) throws BizException;

}
