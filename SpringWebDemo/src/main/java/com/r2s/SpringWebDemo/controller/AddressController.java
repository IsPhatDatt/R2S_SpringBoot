package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.service.AddressService;
import com.r2s.SpringWebDemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping(value = "/get-all")
    public ResponseEntity getAllAddress(Pageable pageable) {

        PagingResponseDTO pagingResponseDTO = addressService.getAllAddress(pageable);

        return new ResponseEntity<>(pagingResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{address-id}")
    public ResponseEntity getAddressById(@PathVariable("address-id") Integer addressId) {
        AddressResponseDTO addressResponseDTO = this.addressService.getAddressById(addressId);

        return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertAddress(@RequestBody CreateAddressRequestDTO createAddressRequestDTO) {
        AddressResponseDTO addressResponseDTO = this.addressService.createAddress(createAddressRequestDTO);

        return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{address-id}")
    public ResponseEntity updateAddress(@PathVariable("address-id") Integer addressId,
                                     @RequestBody UpdateAddressRequestDTO updateAddressRequestDTO) {
        UpdateAddressResponseDTO response = this.addressService.updateAddress(addressId, updateAddressRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{address-id}")
    public ResponseEntity deleteAddress(@PathVariable("address-id") Integer addressId) {
        this.addressService.deleteAddressTemporarily(addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
