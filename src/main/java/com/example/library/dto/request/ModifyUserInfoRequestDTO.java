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
@ApiModel(value = "modify user info parameter")
public class ModifyUserInfoRequestDTO extends Request {

  @NotEmpty(message = "Username cannot be empty")
  @ApiModelProperty(value = "userName", required = true, example = "Achilles")
  @JsonProperty(value = "userName")
  private String userName;

  @NotEmpty(message = "phone cannot be empty")
  @ApiModelProperty(value = "phone", example = "8339012345")
  @JsonProperty(value = "phone")
  private String phone;

  @ApiModelProperty(value = "email", required = true, example = "11@@qq.com")
  @JsonProperty(value = "email")
  private String email;

  @ApiModelProperty(hidden = true)
  private String userId;
}
