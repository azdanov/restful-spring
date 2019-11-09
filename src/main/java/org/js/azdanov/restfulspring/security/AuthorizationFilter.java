package org.js.azdanov.restfulspring.security;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class AuthorizationFilter extends BasicAuthenticationFilter {

  AuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String header = request.getHeader(SecurityConstants.HEADER_STRING);
    if (Objects.isNull(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);

    if (Objects.isNull(token)) {
      return null;
    }

    token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

    String user =
        Jwts.parser()
            .setSigningKey(SecurityConstants.getTokenSecret())
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

    if (Objects.isNull(user)) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(user, null, List.of());
  }
}
