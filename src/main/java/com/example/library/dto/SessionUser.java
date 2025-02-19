package com.example.library.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.pool2.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class SessionUser extends BaseObject {

  String userName;
  Byte userType;
}
