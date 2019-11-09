package org.js.azdanov.restfulspring.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.js.azdanov.restfulspring.ui.model.request.UserDetailsRequestModel;
import org.js.azdanov.restfulspring.ui.model.response.OperationStatusModel;
import org.js.azdanov.restfulspring.ui.model.response.RequestOperationName;
import org.js.azdanov.restfulspring.ui.model.response.RequestOperationStatus;
import org.js.azdanov.restfulspring.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired UserService userService;

  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<UserRest> getUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "2") int limit) {
    List<UserDto> userDtos = userService.getUsers(page, limit);

    return userDtos.stream()
        .map(
            userDto -> {
              UserRest userRest = new UserRest();
              BeanUtils.copyProperties(userDto, userRest);
              return userRest;
            })
        .collect(Collectors.toList());
  }

  @GetMapping(
      path = "/{userId}",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserRest getUser(@PathVariable String userId) {
    UserRest userRest = new UserRest();
    UserDto userDto = userService.getUserByUserId(userId);
    BeanUtils.copyProperties(userDto, userRest);
    return userRest;
  }

  @PostMapping(
      consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);
    UserDto createdUser = userService.createUser(userDto);
    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(createdUser, userRest);
    return userRest;
  }

  @PutMapping(
      path = "/{userId}",
      consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserRest updateUser(
      @PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);
    UserDto updatedUser = userService.updateUser(userId, userDto);
    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(updatedUser, userRest);
    return userRest;
  }

  @DeleteMapping(
      path = "/{userId}",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public OperationStatusModel deleteUser(@PathVariable String userId) {
    OperationStatusModel statusModel = new OperationStatusModel();
    statusModel.setOperationName(RequestOperationName.DELETE.name());

    userService.deleteUser(userId);

    statusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
    return statusModel;
  }
}
