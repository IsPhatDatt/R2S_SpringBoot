package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.*;
import com.r2s.SpringWebDemo.dto.response.*;

import java.util.List;

public interface AddressService {

    List<AddressResponseDTO> getAllAddress(Integer page, Integer size);

    AddressResponseDTO getAddressById(Integer addressId);

    AddressResponseDTO createAddress(CreateAddressRequestDTO createAddressRequestDTO);

    UpdateAddressResponseDTO updateAddress(Integer addressId, UpdateAddressRequestDTO updateAddressRequestDTO);

    Boolean deleteAddress(Integer addressId);
}
