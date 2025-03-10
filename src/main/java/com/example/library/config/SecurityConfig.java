package com.example.library.config;

import com.example.library.filter.JwtFilter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**",
            "/user/withoutToken/**")
        .permitAll() // allow Swagger url
        .anyRequest().permitAll() // other request
        .and()
        .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class) // register JwtFilter
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

  }

  @Bean
  public JwtFilter jwtFilter() {
    return new JwtFilter(); // create JwtFilter instance
  }

//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 允许的前端地址
//    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 允许的 HTTP 方法
//    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 明确允许的请求头
//    configuration.setAllowCredentials(true); // 允许携带凭证（如 Cookie）
//    configuration.setMaxAge(3600L); // 预检请求缓存时间（秒）
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration); // 对所有路径生效
//    return source;
//  }
}