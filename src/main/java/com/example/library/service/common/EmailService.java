package com.example.library.service.common;


import com.example.library.dto.response.EmailValidateCodeRequestDTO;
import com.example.library.exception.BizException;
import javax.servlet.http.HttpSession;

public interface EmailService {

  /**
   * send verification code
   *
   * @param requestDTO
   * @param session
   * @throws Exception
   */
  void sendEmailValidateCode(EmailValidateCodeRequestDTO requestDTO,
      HttpSession session) throws BizException;

}
