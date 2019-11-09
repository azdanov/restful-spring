package org.js.azdanov.restfulspring.service.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.exceptions.ServiceException;
import org.js.azdanov.restfulspring.io.entity.AddressEntity;
import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.js.azdanov.restfulspring.io.repository.UserAddressRepository;
import org.js.azdanov.restfulspring.io.repository.UserRepository;
import org.js.azdanov.restfulspring.service.UserAddressService;
import org.js.azdanov.restfulspring.shared.dto.AddressDto;
import org.js.azdanov.restfulspring.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

  private final UserRepository userRepository;
  private final UserAddressRepository userAddressRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<AddressDto> getUserAddresses(UUID userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    List<AddressEntity> addressEntities = userAddressRepository.findAllByUserDetails(userEntity);

    Type listType = new TypeToken<List<AddressDto>>() {}.getType();
    return modelMapper.map(addressEntities, listType);
  }

  @Override
  public AddressDto getUserAddress(UUID userId, UUID addressId) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (Objects.isNull(userEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    AddressEntity addressEntity =
        userEntity.getAddresses().stream()
            .filter(address -> address.getAddressId().equals(addressId))
            .findFirst()
            .orElseThrow(
                () -> new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

    return modelMapper.map(addressEntity, AddressDto.class);
  }
}
