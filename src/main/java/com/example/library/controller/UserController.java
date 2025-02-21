package com.example.library.controller;


import com.example.library.common.base.ResponseResult;
import com.example.library.dto.request.ModifyUserInfoRequestDTO;
import com.example.library.dto.request.PasswordModifyRequestDTO;
import com.example.library.dto.response.UpdateInfoResponse;
import com.example.library.dto.response.UserInfoResponse;
import com.example.library.exception.BizException;
import com.example.library.service.user.UserModifyService;
import com.example.library.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/withToken")
@AllArgsConstructor
@Api(tags = "User API")
public class UserController {

  private final UserService userService;
  private final UserModifyService userModifyService;
  /**
   * user logout
   *
   * @return
   */
  @PostMapping("/logout")
  @RequestMapping(value = "userLogout", method = RequestMethod.POST)
  public ResponseResult userLogout(@RequestHeader("Authorization") String token,
      HttpServletRequest httpServletRequest) {
    userService.userLogout(token, httpServletRequest);
    return ResponseResult.ofSuccess();
  }

  /**
   * query user detail info
   * @param token
   * @return
   */
  @GetMapping("/queryUserInfo")
  @RequestMapping(value = "queryUserInfo", method = RequestMethod.GET)
  public ResponseResult<UserInfoResponse> queryUserInfo(
      @RequestHeader("Authorization") String token) {
    try {
      return ResponseResult.ofSuccess(userService.getUserInfo(token));
    } catch (BizException e) {
      log.error("query user info error", e);
      return ResponseResult.ofError(e.getCode().getCode(), e.getMessage());
    }
  }

  /**
   * modify user detail info
   * @param token
   * @param requestDTO
   * @return
   */
  @PostMapping("/modifyUserInfo")
  @RequestMapping(value = "modifyUserInfo", method = RequestMethod.POST)
  public ResponseResult modifyUserInfo(@RequestHeader("Authorization") String token,
      @ApiParam(value = "User details", required = true) @RequestBody ModifyUserInfoRequestDTO requestDTO) {
    try {
      return ResponseResult.ofSuccess(userModifyService.updateUserInfo(requestDTO, token));
    } catch (BizException e) {
      log.error("modify user info error", e);
      return ResponseResult.ofError(e.getCode().getCode(), e.getMessage());
    }
  }

  /**
   * modify user password
   * @param token
   * @param requestDTO
   * @return
   */
  @PostMapping(value = "/modifyPassword")
  @ApiOperation("modifyPassword")
  public ResponseResult<UpdateInfoResponse> modifyPassword(
      @RequestHeader("Authorization") String token,
      @ApiParam(value = "modify user password", required = true) @RequestBody PasswordModifyRequestDTO requestDTO) {
    try {
      return ResponseResult.ofSuccess(userModifyService.modifyPassword(requestDTO));
    } catch (BizException e) {
      log.error("modify password error", e);
      return ResponseResult.ofError(e.getCode().getCode(), e.getMessage());
    }
  }


}
