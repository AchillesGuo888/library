package com.example.library.dto;

import lombok.Data;

/**
 * <pre>
 *     Json格式
 * </pre>
 *
 * @author : saysky
 * @date : 2018/5/24
 */
@Data
public class JsonResult {

  /**
   * 返回的状态码，0：失败，1：成功
   */
  private Integer code;

  /**
   * 返回信息
   */
  private String msg;

  /**
   * 返回的数据
   */
  private Object result;

  /**
   * 不返回数据的构造方法
   *
   * @param code 状态码
   * @param msg  信息
   */
  public JsonResult(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  /**
   * 返回数据的构造方法
   *
   * @param code   状态码
   * @param msg    信息
   * @param result 数据
   */
  public JsonResult(Integer code, String msg, Object result) {
    this.code = code;
    this.msg = msg;
    this.result = result;
  }

  /**
   * 返回状态码和数据
   *
   * @param code   状态码
   * @param result 数据
   */
  public JsonResult(Integer code, Object result) {
    this.code = code;
    this.result = result;
  }

  public static JsonResult error(String msg) {
    return new JsonResult(0, msg);
  }

  public static JsonResult error(String msg, Object data) {
    return new JsonResult(0, msg, data);
  }

  public static JsonResult success() {
    return new JsonResult(1, "操作成功");
  }

  public static JsonResult success(String msg) {
    return new JsonResult(1, msg);
  }

  public static JsonResult success(String msg, Object result) {
    return new JsonResult(1, msg, result);
  }
}
