package com.example.library.service.user;


import com.example.library.dto.request.ForgetPasswordRequestDTO;
import com.example.library.dto.request.ModifyUserInfoRequestDTO;
import com.example.library.dto.request.PasswordModifyRequestDTO;
import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;
import com.example.library.dto.response.UpdateInfoResponse;
import com.example.library.dto.response.UserInfoResponse;
import com.example.library.exception.BizException;
import javax.servlet.http.HttpServletRequest;


public interface UserModifyService {

  /**
   * update user info
   * @param requestDTO
   * @param token
   * @return
   * @throws BizException
   */
  UpdateInfoResponse updateUserInfo(ModifyUserInfoRequestDTO requestDTO, String token)
      throws BizException;

  /**
   * modify user password
   * @param requestDTO
   * @return
   * @throws BizException
   */
  UpdateInfoResponse modifyPassword(PasswordModifyRequestDTO requestDTO) throws BizException;

  /**
   * user forget password
   *
   * @param requestDTO
   * @return
   * @throws BizException
   */
  UpdateInfoResponse forgetPassword(ForgetPasswordRequestDTO requestDTO) throws BizException;

}
