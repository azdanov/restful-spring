package org.js.azdanov.restfulspring.shared;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class Utils {

  private static final String ALPHA_NUM =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final Random random = new SecureRandom();

  public String generateUserId(int length) {
    return generateRandomString(length);
  }

  private String generateRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      stringBuilder.append(ALPHA_NUM.charAt(random.nextInt(ALPHA_NUM.length())));
    }

    return new String(stringBuilder);
  }
}
