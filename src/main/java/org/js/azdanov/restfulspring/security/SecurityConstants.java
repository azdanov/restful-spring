package org.js.azdanov.restfulspring.security;

import org.js.azdanov.restfulspring.AppProperties;
import org.js.azdanov.restfulspring.SpringApplicationContext;

class SecurityConstants {

  static final long EXPIRATION_TIME = 864000000;
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";
  static final String SIGN_UP_URL = "/users";

  private SecurityConstants() {
    throw new UnsupportedOperationException();
  }

  static String getTokenSecret() {
    AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
    return appProperties.getTokenSecret();
  }
}
