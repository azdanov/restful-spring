package org.js.azdanov.restfulspring.controller;

import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.js.azdanov.restfulspring.ui.model.request.UserDetailsRequestModel;
import org.js.azdanov.restfulspring.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired UserService userService;

  @GetMapping(path = "/{userId}")
  public UserRest getUser(@PathVariable String userId) {
    UserRest userRest = new UserRest();
    UserDto userDto = userService.getUserByUserId(userId);
    BeanUtils.copyProperties(userDto, userRest);

    return userRest;
  }

  @PostMapping
  public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
    UserRest userRest = new UserRest();

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);

    UserDto createdUser = userService.createUser(userDto);
    BeanUtils.copyProperties(createdUser, userRest);

    return userRest;
  }

  @PutMapping
  public String updateUser() {
    return "updateUser was called";
  }

  @DeleteMapping
  public String deleteUser() {
    return "deleteUser was called";
  }
}
