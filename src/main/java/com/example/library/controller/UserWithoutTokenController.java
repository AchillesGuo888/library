package com.example.library.controller;

import com.example.library.common.base.ResponseResult;
import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;

import com.example.library.exception.BizException;

import com.example.library.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/withoutToken")
@Api(tags = "User without token API")
@AllArgsConstructor
public class UserWithoutTokenController {

  private final UserService userService;

  /**
   * user login
   *
   * @return
   */
  @PostMapping("/login")
  @RequestMapping(value = "login", method = RequestMethod.POST)
  public ResponseResult<RegisterResponse> login(
      @ApiParam(value = "User details", required = true) @RequestBody UserLoginRequestDTO requestDTO,
      HttpServletRequest httpServletRequest) {
    try {
      return ResponseResult.ofSuccess(userService.userLogin(requestDTO, httpServletRequest));
    } catch (BizException e) {
      log.error("user login error", e);
      return ResponseResult.ofError(e.getCode().getCode(), e.getMessage());
    }


  }

}
