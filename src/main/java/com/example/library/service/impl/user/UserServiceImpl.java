package com.example.library.service.impl.user;


import com.example.library.dto.request.UserLoginRequestDTO;
import com.example.library.dto.response.RegisterResponse;

import com.example.library.exception.BizException;

import com.example.library.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  @Override
  public RegisterResponse userLogin(UserLoginRequestDTO requestDTO,
      HttpServletRequest httpServletRequest) throws BizException {

    return null;
  }




}
