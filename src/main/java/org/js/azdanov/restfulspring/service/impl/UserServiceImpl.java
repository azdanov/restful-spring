package org.js.azdanov.restfulspring.service.impl;

import java.util.List;
import java.util.Objects;
import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.js.azdanov.restfulspring.io.repository.UserRepository;
import org.js.azdanov.restfulspring.service.UserService;
import org.js.azdanov.restfulspring.shared.Utils;
import org.js.azdanov.restfulspring.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private Utils utils;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.utils = utils;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity existingUserEntity = userRepository.findByEmail(userDto.getEmail());

    if (Objects.nonNull(existingUserEntity)) {
      throw new RuntimeException("Record already exists");
    }

    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(userDto, userEntity);

    String publicUserId = utils.generateUserId(30);
    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    UserEntity savedUserEntity = userRepository.save(userEntity);

    UserDto savedUserDto = new UserDto();
    BeanUtils.copyProperties(savedUserEntity, savedUserDto);

    return savedUserDto;
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new UsernameNotFoundException(email);
    }

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userEntity, userDto);

    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (Objects.isNull(userEntity)) {
      throw new UsernameNotFoundException(email);
    }

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), List.of());
  }
}
