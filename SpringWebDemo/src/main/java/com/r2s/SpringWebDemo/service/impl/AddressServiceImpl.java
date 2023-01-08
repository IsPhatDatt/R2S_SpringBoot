package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Address;
import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.repository.AddressRepository;
import com.r2s.SpringWebDemo.repository.ProductRepository;
import com.r2s.SpringWebDemo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressResponseDTO> getAllAddress(Integer page, Integer size) {

        List<AddressResponseDTO> addressResponseDTOList = new ArrayList<>();
        List<Address> addresses = addressRepository.getAllAddress(page, size);
        for (Address address : addresses) {
            AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
            addressResponseDTO.setAddressFull(address.getAddressFull());
            addressResponseDTO.setId(address.getId());
            addressResponseDTOList.add(addressResponseDTO);
        }

        return addressResponseDTOList;
    }

    @Override
    public AddressResponseDTO getAddressById(Integer addressId) {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        try {
            Optional<Address> address = addressRepository.findById(addressId);
            if(address.isPresent() && address.get().getIsDeleted() == AddressIsDeleteFalse) {
                addressResponseDTO.setId(address.get().getId());
                addressResponseDTO.setAddressFull(address.get().getAddressFull());
            } else {
                throw new NoSuchElementException("Can't find addressId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return addressResponseDTO;
    }

    @Override
    public AddressResponseDTO createAddress(CreateAddressRequestDTO createAddressRequestDTO) {

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        Address address = new Address();

        try {
            if(createAddressRequestDTO.getAddressFull().isEmpty()) {
                throw new Exception("Address is required!");
            }
            else {
                address.setAddressFull(createAddressRequestDTO.getAddressFull());
                if(address.getCreatedDate() == null) {
                    address.setCreatedDate(new Date());
                }
                if(address.getUpdatedDate() == null) {
                    address.setUpdatedDate(new Date());
                }
                address.setIsDeleted(ProductIsDeleteFalse);
                addressResponseDTO.setId(address.getId());
                addressResponseDTO.setAddressFull(address.getAddressFull());

                addressRepository.save(address);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return addressResponseDTO;
    }

    @Override
    public UpdateAddressResponseDTO updateAddress(Integer addressId, UpdateAddressRequestDTO updateAddressRequestDTO) {

        UpdateAddressResponseDTO updateAddressResponseDTO = new UpdateAddressResponseDTO();
        Optional<Address> address = this.addressRepository.findById(addressId);

        try {
            if(address.isEmpty() || address.get().getIsDeleted() == AddressIsDeleteTrue) {
                throw new Exception("Can't find addressId");
            }
            if(updateAddressRequestDTO.getAddressFull().isEmpty()) {
                throw new Exception("Address is required!");
            }
            else {
                address.get().setId(addressId);
                if(!address.get().getAddressFull().equals(updateAddressRequestDTO.getAddressFull())) {
                    address.get().setAddressFull(updateAddressRequestDTO.getAddressFull());
                }
                address.get().setUpdatedDate(new Date());
                updateAddressResponseDTO.setId(address.get().getId());
                updateAddressResponseDTO.setAddressFull(address.get().getAddressFull());

                addressRepository.save(address.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return updateAddressResponseDTO;
    }

    @Override
    public Boolean deleteAddress(Integer addressId) {

        try {
            Optional<Address> address = addressRepository.findById(addressId);
            if(address.isPresent()) {
                this.addressRepository.deleteById(addressId);
                return true;
            } else {
                throw new NoSuchElementException("Can't find addressId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
