package org.js.azdanov.restfulspring.service;

import java.util.List;
import java.util.UUID;
import org.js.azdanov.restfulspring.shared.dto.AddressDto;

public interface UserAddressService {

  List<AddressDto> getUserAddresses(UUID userId);

  AddressDto getUserAddress(UUID userId, UUID addressId);
}
