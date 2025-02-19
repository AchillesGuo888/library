package com.example.library.dto.request;

import com.example.library.dto.request.Base.Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@ApiModel("login request parameter")
public class UserLoginRequestDTO extends Request {

  @NotEmpty(message = "email cannot be empty")
  @ApiModelProperty(value = "email", required = true, example = "11@qq.com")
  @JsonProperty(value = "email")
  private String email;

  @NotEmpty(message = "Password cannot be empty")
  @ApiModelProperty(value = "password", required = true, example = "123456")
  @JsonProperty(value = "password")
  private String password;


}
