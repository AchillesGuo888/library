package com.example.library.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * verification code body
 */

public class Verification {
  private static final long serialVersionUID = 1L;

  public String code;
  private LocalDateTime createTime;

}
