package org.js.azdanov.restfulspring.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.js.azdanov.restfulspring.ui.model.request.UserDetailsRequestModel;
import org.js.azdanov.restfulspring.ui.model.response.OperationStatusModel;
import org.js.azdanov.restfulspring.ui.model.response.RequestOperationName;
import org.js.azdanov.restfulspring.ui.model.response.RequestOperationStatus;
import org.js.azdanov.restfulspring.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
@AllArgsConstructor
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<UserRest> getUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "2") int limit) {
    List<UserDto> userDtos = userService.getUsers(page, limit);

    Type listType = new TypeToken<List<UserRest>>() {}.getType();
    return modelMapper.map(userDtos, listType);
  }

  @GetMapping(
      path = "/{userId}",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserRest getUser(@PathVariable UUID userId) {
    UserDto userDto = userService.getUserByUserId(userId);

    return modelMapper.map(userDto, UserRest.class);
  }

  @PostMapping(
      consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
    UserDto userDto = modelMapper.map(userDetails, UserDto.class);

    UserDto createdUser = userService.createUser(userDto);

    return modelMapper.map(createdUser, UserRest.class);
  }

  @PutMapping(
      path = "/{userId}",
      consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserRest updateUser(
      @PathVariable UUID userId, @RequestBody UserDetailsRequestModel userDetails) {
    UserDto userDto = modelMapper.map(userDetails, UserDto.class);
    UserDto updatedUser = userService.updateUser(userId, userDto);

    return modelMapper.map(updatedUser, UserRest.class);
  }

  @DeleteMapping(
      path = "/{userId}",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public OperationStatusModel deleteUser(@PathVariable UUID userId) {
    userService.deleteUser(userId);

    OperationStatusModel statusModel = new OperationStatusModel();
    statusModel.setOperationName(RequestOperationName.DELETE.name());
    statusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

    return statusModel;
  }
}
