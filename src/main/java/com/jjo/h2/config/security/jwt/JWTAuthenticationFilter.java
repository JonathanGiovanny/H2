package com.jjo.h2.config.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.LoginDTO;
import com.jjo.h2.exception.ErrorConstants;
import com.jjo.h2.exception.HException;
import com.jjo.h2.services.security.JWTService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements AuthenticationEntryPoint {

  private final AuthenticationManager authenticationManager;

  private final JWTService jwtService;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
    super();
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;

    // This method should be called here since outside cannot be override
    super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.AUTH_LOGIN_URL, HttpMethod.POST.name()));
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
      throw new HException(ErrorConstants.MISMATCH_INPUT);
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

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    response.addHeader("WWW-Authenticate", "Basic realm=\"JWT\"");
    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
  }
}
