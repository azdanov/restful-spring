package org.js.azdanov.restfulspring.service;

import java.util.List;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto user);

  UserDto getUser(String email);

  UserDto getUserByUserId(String userId);

  UserDto updateUser(String userId, UserDto userDto);

  void deleteUser(String userId);

  List<UserDto> getUsers(int page, int limit);
}
