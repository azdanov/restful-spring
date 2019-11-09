package org.js.azdanov.restfulspring.service.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.exceptions.ServiceException;
import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.js.azdanov.restfulspring.io.repository.UserRepository;
import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.js.azdanov.restfulspring.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ModelMapper modelMapper;

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity existingUserEntity = userRepository.findByEmail(userDto.getEmail());

    if (Objects.nonNull(existingUserEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    userDto
        .getAddresses()
        .forEach(
            addressDto -> {
              addressDto.setUserDetails(userDto);
              addressDto.setAddressId(UUID.randomUUID());
            });

    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

    userEntity.setUserId(UUID.randomUUID());
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    UserEntity savedUserEntity = userRepository.save(userEntity);

    return modelMapper.map(savedUserEntity, UserDto.class);
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(UUID userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto updateUser(UUID userId, UserDto userDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    UserEntity updatedUserDetails = userRepository.save(userEntity);

    return modelMapper.map(updatedUserDetails, UserDto.class);
  }

  @Override
  public void deleteUser(UUID userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
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

    Type listType = new TypeToken<List<UserDto>>() {}.getType();
    return modelMapper.map(userEntities, listType);
  }

  @Override
  public UUID getUserId(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return userEntity.getUserId();
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), List.of());
  }
}
