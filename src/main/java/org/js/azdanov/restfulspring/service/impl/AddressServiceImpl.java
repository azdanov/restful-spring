package org.js.azdanov.restfulspring.service.impl;

import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.exceptions.ServiceException;
import org.js.azdanov.restfulspring.io.entity.AddressEntity;
import org.js.azdanov.restfulspring.io.repository.UserAddressRepository;
import org.js.azdanov.restfulspring.service.AddressService;
import org.js.azdanov.restfulspring.shared.dto.AddressDto;
import org.js.azdanov.restfulspring.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

  private UserAddressRepository userAddressRepository;
  private ModelMapper modelMapper;

  @Override
  public AddressDto getUserAddress(UUID addressId) {
    AddressEntity addressEntity = userAddressRepository.findByAddressId(addressId);

    if (Objects.isNull(addressEntity)) {
      throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }

    return modelMapper.map(addressEntity, AddressDto.class);
  }
}
