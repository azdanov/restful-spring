package org.js.azdanov.restfulspring.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.exceptions.UserServiceException;
import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.js.azdanov.restfulspring.io.repository.UserRepository;
import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.Utils;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.js.azdanov.restfulspring.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final Utils utils;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ModelMapper modelMapper;

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity existingUserEntity = userRepository.findByEmail(userDto.getEmail());

    if (Objects.nonNull(existingUserEntity)) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

    String publicUserId = utils.generateUserId(30);
    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    UserEntity savedUserEntity = userRepository.save(userEntity);

    return modelMapper.map(savedUserEntity, UserDto.class);
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto updateUser(String userId, UserDto userDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    UserEntity updatedUserDetails = userRepository.save(userEntity);

    return modelMapper.map(updatedUserDetails, UserDto.class);
  }

  @Override
  public void deleteUser(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    userRepository.delete(userEntity);
  }

  @Override
  public List<UserDto> getUsers(int page, int limit) {

    if (page > 0) {
      page = page - 1;
    } else if (page < 0) {
      page = 0;
    }

    PageRequest pageable = PageRequest.of(page, limit);
    Page<UserEntity> usersPage = userRepository.findAll(pageable);
    List<UserEntity> userEntities = usersPage.getContent();

    return userEntities.stream()
        .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), List.of());
  }
}
