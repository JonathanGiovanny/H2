package com.jjo.h2.config.security.jwt;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.LoginDTO;
import com.jjo.h2.exception.ErrorConstants;
import com.jjo.h2.exception.HException;
import com.jjo.h2.services.security.JWTService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final @NonNull AuthenticationManager authenticationManager;

  private final @NonNull JWTService jwtService;

  @PostConstruct
  private void postConstruct() {
    super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      // Get Data from the request
      final LoginDTO loginData = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

      // Generate the token for the auth process
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword());

      // Authenticate
      return authenticationManager.authenticate(token);

    } catch (IOException e) {
      throw new HException(ErrorConstants.MISMATCH_INPUT, e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
      throws IOException, ServletException {
    // Generate token
    final String token = jwtService.generateToken(authentication);

    // Add token to header
    response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
  }
}
