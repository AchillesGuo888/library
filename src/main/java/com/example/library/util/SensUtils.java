package com.example.library.util;

import io.github.biezhi.ome.OhMyEmail;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *     常用工具
 * </pre>
 */
@Slf4j
public class SensUtils {

  /**
   * 配置邮件
   *
   * @param smtpHost smtpHost
   * @param userName 邮件地址
   * @param password password
   */
  public static void configMail(String smtpHost, String userName, String password) {
    Properties properties = OhMyEmail.defaultConfig(false);
    properties.setProperty("mail.smtp.host", smtpHost);
    OhMyEmail.config(properties, userName, password);
  }


  public static String listToStr(List<String> list) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : list) {
      stringBuilder.append(str).append(",");
    }
    String temp = stringBuilder.toString();
    if (temp.length() > 0) {
      return temp.substring(0, temp.length() - 1);
    }
    return temp;
  }

  public static void main(String[] args) {
    List<String> list = new ArrayList<>();
    list.add("11");
    list.add("22");
    list.add("13");
    System.out.println(listToStr(list));
  }

}
