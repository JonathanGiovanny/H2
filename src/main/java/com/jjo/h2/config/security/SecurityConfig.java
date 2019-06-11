package com.jjo.h2.config.security;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
import com.jjo.h2.config.security.jwt.JWTAuthenticationFilter;
import com.jjo.h2.config.security.jwt.JWTAuthorizationFilter;
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
        .and().exceptionHandling().authenticationEntryPoint((request, response, exception) -> generateUnauthorizedEntry(request, response, exception)) //
        .and().authorizeRequests().antMatchers(HttpMethod.GET, SecurityConstants.SECURITY_PATH + "singup/checkname/**").permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.SECURITY_PATH + "singup").permitAll() //
        .anyRequest().authenticated() // Other requests authenticated
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
    configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * 
   * @param request
   * @param response
   * @param e
   * @throws JsonProcessingException
   * @throws IOException
   */
  private void generateUnauthorizedEntry(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws JsonProcessingException, IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=\"JWT\"");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
  }
}
