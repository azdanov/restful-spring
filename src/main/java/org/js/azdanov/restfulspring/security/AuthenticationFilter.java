package org.js.azdanov.restfulspring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.js.azdanov.restfulspring.SpringApplicationContext;
import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.js.azdanov.restfulspring.ui.model.request.UserLoginRequestModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {

    try {
      UserLoginRequestModel loginRequestModel =
          new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
      log.info("Logging in: {}", loginRequestModel.getEmail());
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequestModel.getEmail(), loginRequestModel.getPassword(), List.of()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {
    String username = ((User) authResult.getPrincipal()).getUsername();
    String token =
        Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
            .compact();

    UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

    UserDto userDto = userService.getUser(username);

    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    response.addHeader("UserID", userDto.getUserId());
  }
}
