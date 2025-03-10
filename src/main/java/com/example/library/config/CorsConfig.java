package com.example.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // 匹配所有接口路径
        .allowedOrigins("http://localhost:3000")  // 允许的前端地址
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")// 允许所有 HTTP 方法
        .allowedHeaders("*")                     // 允许所有请求头
        .allowCredentials(true)                  // 允许携带凭证（如 Cookie）
        .maxAge(3600);                           // 预检请求缓存时间（秒）
  }

}
