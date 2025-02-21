package com.example.library.service.impl.common;

import com.example.library.dto.response.EmailValidateCodeRequestDTO;
import com.example.library.exception.BizException;
import com.example.library.service.common.EmailService;
import com.example.library.util.FreeMarkerTemplateUtil;
import com.example.library.util.VerificationCodeUtil;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final VerificationCodeUtil verificationCodeUtil;

  /**
   * send verification code email
   *
   * @param requestDTO
   * @param session
   */
  @Override
  public void sendEmailValidateCode(EmailValidateCodeRequestDTO requestDTO,
      HttpSession session) throws BizException {
    verificationCodeUtil.sendCodeToUser(requestDTO, session);
  }

}
