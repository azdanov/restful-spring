package org.js.azdanov.restfulspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RestfulSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestfulSpringApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SpringApplicationContext springApplicationContext() {
    return new SpringApplicationContext();
  }

  @Bean("AppProperties")
  public AppProperties getAppProperties() {
    return new AppProperties();
  }
}
