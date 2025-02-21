package com.example.library.service.impl.user;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.library.common.base.ResponseCode;
import com.example.library.dto.SessionUser;
import com.example.library.dto.request.ForgetPasswordRequestDTO;
import com.example.library.dto.request.ModifyUserInfoRequestDTO;
import com.example.library.dto.request.PasswordModifyRequestDTO;
import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;
import com.example.library.dto.response.UpdateInfoResponse;
import com.example.library.dto.response.UserInfoResponse;
import com.example.library.entity.UserInfo;
import com.example.library.entity.UserInfoExample;
import com.example.library.exception.BizException;
import com.example.library.mapper.UserInfoMapper;
import com.example.library.service.auth.AbstractAuth;
import com.example.library.service.auth.Auth4EmailPasswordMatch;
import com.example.library.service.user.UserModifyService;
import com.example.library.service.user.UserService;
import com.example.library.util.JwtUtil;
import com.example.library.util.Md5Util;
import com.example.library.util.UserUtil;
import com.example.library.util.ValidateUtils;
import com.example.library.util.VerificationCodeUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserModifyServiceImpl implements UserModifyService {

  private final UserInfoMapper userInfoMapper;
  private final JwtUtil jwtUtil;
  private final UserUtil userUtil;
  private final VerificationCodeUtil verificationCodeUtil;

  @Override
  public UpdateInfoResponse updateUserInfo(ModifyUserInfoRequestDTO requestDTO, String token)
      throws BizException {
    String userId = jwtUtil.getUserIdFromToken(token);
    if (StringUtils.isEmpty(userId)) {
      throw new BizException(ResponseCode.token_error);
    }
    UserInfo user = userUtil.findUserByUserId(userId);

    if (!user.getEmail().equals(requestDTO.getEmail())) {
      //check new email
      UserInfo existUser = userUtil.findUserByEmail(requestDTO.getEmail());
      if (ObjectUtil.isNotNull(existUser)) {
        throw new BizException(ResponseCode.email_has_exist);
      }
    }
    BeanUtils.copyProperties(requestDTO, user);
    user.setUserId(userId);
    UserInfoExample example = new UserInfoExample();
    UserInfoExample.Criteria criteria = example.createCriteria();
    criteria.andIdEqualTo(user.getId());
    userInfoMapper.updateByExample(user, example);

    return UpdateInfoResponse.builder().result(true).build();
  }

  @Override
  public UpdateInfoResponse modifyPassword(PasswordModifyRequestDTO requestDTO)
      throws BizException {
    /**
     * 1.check parameter
     */
    String email = requestDTO.getEmail();
    String newPassword = requestDTO.getNewPassword();
    String oldPassword = requestDTO.getOldPassword();
    checkParams(email,newPassword,oldPassword);


    // query user info
    UserInfo user = userUtil.findUserByEmail(requestDTO.getEmail());
    if (user == null) {
      throw new BizException(ResponseCode.email_not_exist);
    }

    /**
     * 2.check old password
     */
    String userId = user.getUserId();
    String salt = user.getSalt();
    String passwordInDb = user.getPassword();
    String oldPasswordInDb = Md5Util.getSaltMd5AndSha(oldPassword, salt);
    if (!passwordInDb.equals(oldPasswordInDb)) {
      throw new BizException(ResponseCode.password_old_not_correct);
    }

    // modify password
    changePassword(userId,newPassword,salt);

    return UpdateInfoResponse.builder().result(true).build();
  }

  private void checkParams(String email, String newPassword, String oldPassword) throws BizException {

    if (StrUtil.isBlank(email)) {
      throw new BizException(ResponseCode.email_error_rules);
    }
    if (StrUtil.isBlank(oldPassword)) {
      throw new BizException(ResponseCode.password_old_error_rules);
    }
    if (StrUtil.isBlank(newPassword)) {
      throw new BizException(ResponseCode.password_new_error_rules);
    }
    if (newPassword.equals(oldPassword)) {
      throw new BizException(ResponseCode.password_old_new_same);
    }
  }

  private void changePassword(String userId, String newPassword, String salt) throws BizException {
    UserInfo update = new UserInfo();
    update.setUserId(userId);
    String newPasswordInDb = Md5Util.getSaltMd5AndSha(newPassword, salt);
    update.setPassword(newPasswordInDb);

    //update DB
    UserInfoExample example = new UserInfoExample();
    UserInfoExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userId);
    int count = userInfoMapper.updateByExampleSelective(update, example);
    if (count == 0) {
      throw new BizException(ResponseCode.server_err);
    }
  }

  @Override
  public UpdateInfoResponse forgetPassword(ForgetPasswordRequestDTO requestDTO)
      throws BizException {
    UserInfo user = userUtil.findUserByEmail(requestDTO.getEmail());
    if (user == null) {
      throw new BizException(ResponseCode.email_not_exist);
    }
    if (!verificationCodeUtil.verifyCode(requestDTO.getCode(), requestDTO.getEmail())) {
      throw new BizException(ResponseCode.code_false);
    }

    if (!requestDTO.getNewPassword().equals(requestDTO.getConfirmPassword())) {
      throw new BizException(ResponseCode.password_not_same);
    }
    if (!ValidateUtils.checkPassword(requestDTO.getNewPassword())) {
      throw new BizException(ResponseCode.password_error_rules);
    }

    // get md5 password
    String userId = user.getUserId();
    String newPasswordInDb = createPassWordInDB(user.getSalt(), requestDTO.getNewPassword());

    UserInfo update = new UserInfo();
    update.setPassword(newPasswordInDb);

    // update new password
    UserInfoExample example = new UserInfoExample();
    UserInfoExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userId);
    int count = userInfoMapper.updateByExampleSelective(update, example);

    if (count == 0) {
      throw new BizException(ResponseCode.server_err);
    }
    return UpdateInfoResponse.builder().result(true).build();
  }

  /**
   * get user md5 password
   *
   * @param salt
   * @param password
   * @return
   */
  private String createPassWordInDB(String salt, String password) throws BizException {
    return Md5Util.getSaltMd5AndSha(password, salt);
  }


}
