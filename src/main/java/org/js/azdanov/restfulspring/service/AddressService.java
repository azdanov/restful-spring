package org.js.azdanov.restfulspring.service;

import java.util.UUID;
import org.js.azdanov.restfulspring.shared.dto.AddressDto;

public interface AddressService {

  AddressDto getUserAddress(UUID addressId);
}
