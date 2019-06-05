package com.jjo.h2.config.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjo.h2.config.security.jwt.JWTAuthenticationFilter;
import com.jjo.h2.config.security.jwt.JWTAuthorizationFilter;
import com.jjo.h2.exception.HErrorDTO;
import com.jjo.h2.services.security.JWTService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final @NonNull UserDetailsService userDetailsService;

  private final @NonNull JWTService jwtService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    final JWTAuthenticationFilter authentication = new JWTAuthenticationFilter(authenticationManager(), jwtService);
    final JWTAuthorizationFilter authorization = new JWTAuthorizationFilter(jwtService, userDetailsService);

    http.cors().and().csrf().disable() //
        .httpBasic().disable() //
        .logout().disable();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER) //
        // handle an authorized attempts
        .and().exceptionHandling().authenticationEntryPoint((req, rsp, e) -> generateUnauthorizedEntry(req, rsp, e)) //
        .and().authorizeRequests().anyRequest().authenticated() // Other requests authenticated
        .and().addFilterBefore(authentication, UsernamePasswordAuthenticationFilter.class) //
        .addFilterAfter(authorization, UsernamePasswordAuthenticationFilter.class); //

    // To modify the value of X-FRAME-OPTIONS to allow frames from SameOrigin for h2 DB Console
    http.headers().frameOptions().sameOrigin();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  private void generateUnauthorizedEntry(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws JsonProcessingException, IOException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMsg(e));
    response.addHeader("WWW-Authenticate", "Basic realm=\"JWT\"");
  }
  
  /**
   * Generate JSON with error msg for the unauthorized requests
   * 
   * @param authException
   * @return
   * @throws JsonProcessingException
   */
  private String errorMsg(AuthenticationException authException) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    HErrorDTO exception = HErrorDTO.builder() //
        .userMessage(authException.getMessage()) //
        .techMessage(authException.getMessage()) //
        .eventTime(LocalDateTime.now()) //
        .build();
    return objectMapper.writeValueAsString(exception);
  }
}
