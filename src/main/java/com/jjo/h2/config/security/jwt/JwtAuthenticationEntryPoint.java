package com.jjo.h2.config.security.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjo.h2.exception.HErrorDTO;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Send error if the incoming request is unauthorize
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    ObjectMapper objectMapper = new ObjectMapper();
    HErrorDTO exception = HErrorDTO.builder() //
        .userMessage(authException.getMessage()) //
        .techMessage(authException.getMessage()) //
        .eventTime(LocalDateTime.now()) //
        .build();
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, objectMapper.writeValueAsString(exception));
  }
}
