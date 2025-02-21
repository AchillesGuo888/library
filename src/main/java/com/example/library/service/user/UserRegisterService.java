package com.example.library.service.user;

import com.example.library.dto.request.RegisterRequestDTO;
import com.example.library.dto.response.RegisterResponse;
import com.example.library.exception.BizException;
import javax.servlet.http.HttpServletRequest;

public interface UserRegisterService {

  /**
   * user register interface
   * @param requestDTO
   * @param httpServletRequest
   * @return
   */
  RegisterResponse register(RegisterRequestDTO requestDTO, HttpServletRequest httpServletRequest) throws BizException;;

}
