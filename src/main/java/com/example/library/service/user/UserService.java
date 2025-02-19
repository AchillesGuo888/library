package com.example.library.service.user;


import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;

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


}
