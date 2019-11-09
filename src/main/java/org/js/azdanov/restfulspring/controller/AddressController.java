package org.js.azdanov.restfulspring.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.js.azdanov.restfulspring.service.AddressService;
import org.js.azdanov.restfulspring.shared.dto.AddressDto;
import org.js.azdanov.restfulspring.ui.model.response.AddressRest;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("addresses")
@AllArgsConstructor
public class AddressController {

  private final AddressService addressService;
  private final ModelMapper modelMapper;

  @GetMapping(
      path = "/{addressId}",
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public AddressRest getUserAddress(@PathVariable UUID addressId) {
    AddressDto addressDto = addressService.getUserAddress(addressId);

    return modelMapper.map(addressDto, AddressRest.class);
  }
}
