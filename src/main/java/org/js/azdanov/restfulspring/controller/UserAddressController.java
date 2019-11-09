package org.js.azdanov.restfulspring.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.service.UserAddressService;
import org.js.azdanov.restfulspring.shared.dto.AddressDto;
import org.js.azdanov.restfulspring.ui.model.response.AddressRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserAddressController {

  private final UserAddressService userAddressService;
  private final ModelMapper modelMapper;

  @GetMapping(
      path = "/{userId}/addresses",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<AddressRest> getUserAddresses(@PathVariable UUID userId) {
    List<AddressDto> addressDtos = userAddressService.getUserAddresses(userId);

    Type listType = new TypeToken<List<AddressRest>>() {}.getType();
    return modelMapper.map(addressDtos, listType);
  }

  @GetMapping(
      path = "/{userId}/addresses/{addressId}",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public AddressRest getUserAddress(@PathVariable UUID userId, @PathVariable UUID addressId) {
    AddressDto addressDto = userAddressService.getUserAddress(userId, addressId);

    return modelMapper.map(addressDto, AddressRest.class);
  }
}
