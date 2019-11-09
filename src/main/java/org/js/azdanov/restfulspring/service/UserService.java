package org.js.azdanov.restfulspring.service;

import java.util.List;
import java.util.UUID;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto user);

  UserDto getUser(String email);

  UserDto getUserByUserId(UUID userId);

  UserDto updateUser(UUID userId, UserDto userDto);

  void deleteUser(UUID userId);

  List<UserDto> getUsers(int page, int limit);

  UUID getUserId(String email);
}
