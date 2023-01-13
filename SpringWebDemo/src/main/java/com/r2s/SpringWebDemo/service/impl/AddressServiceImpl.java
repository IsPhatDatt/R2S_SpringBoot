package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Address;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.repository.AddressRepository;
import com.r2s.SpringWebDemo.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PagingResponseDTO getAllAddress(Pageable pageable) {

        Page<Address> addressePage = this.addressRepository.findAllByIsDeleted(ADDRESS_IS_DELETED_FALSE, pageable)
                .orElseThrow(() -> new RuntimeException("Can't get address by paging"));

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setPage(addressePage.getNumber());
        pagingResponseDTO.setTotalPages(addressePage.getTotalPages());
        pagingResponseDTO.setSize(addressePage.getSize());
        pagingResponseDTO.setTotalRecords(addressePage.getTotalElements());

        List<AddressResponseDTO> addressResponseDTOList = addressePage.stream()
                .map((address) -> this.modelMapper.map(address, AddressResponseDTO.class)).collect(Collectors.toList());

        pagingResponseDTO.setResponseObjectList(addressResponseDTOList);

        return pagingResponseDTO;
    }

    @Override
    public AddressResponseDTO getAddressById(Integer addressId) {

        try {
            Optional<Address> address = this.addressRepository.findById(addressId);
            if(address.isPresent() && address.get().getIsDeleted() == ADDRESS_IS_DELETED_FALSE) {
                return this.modelMapper.map(address.get(), AddressResponseDTO.class);
            } else {
                throw new NoSuchElementException("Can't find addressId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public AddressResponseDTO createAddress(CreateAddressRequestDTO createAddressRequestDTO) {

        Address address = new Address();

        try {
            if(createAddressRequestDTO.getApartmentNumber().isEmpty()) {
                throw new Exception("Apartment number is required!");
            }
            if(createAddressRequestDTO.getStreet().isEmpty()) {
                throw new Exception("Street is required!");
            }
            if(createAddressRequestDTO.getWard().isEmpty()) {
                throw new Exception("Ward is required!");
            }
            if(createAddressRequestDTO.getDistrict().isEmpty()) {
                throw new Exception("District is required!");
            }
            if(createAddressRequestDTO.getProvince().isEmpty()) {
                throw new Exception("Province is required!");
            }
            else {
                address.setApartmentNumber(createAddressRequestDTO.getApartmentNumber());
                address.setStreet(createAddressRequestDTO.getStreet());
                address.setWard(createAddressRequestDTO.getWard());
                address.setDistrict(createAddressRequestDTO.getDistrict());
                address.setProvince(createAddressRequestDTO.getProvince());
                if(address.getCreatedDate() == null) {
                    address.setCreatedDate(new Date());
                }
                if(address.getUpdatedDate() == null) {
                    address.setUpdatedDate(new Date());
                }
                address.setIsDeleted(ADDRESS_IS_DELETED_FALSE);

                addressRepository.save(address);

                return this.modelMapper.map(address, AddressResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public UpdateAddressResponseDTO updateAddress(Integer addressId, UpdateAddressRequestDTO updateAddressRequestDTO) {

        Optional<Address> address = this.addressRepository.findById(addressId);

        try {
            if(address.isEmpty() || address.get().getIsDeleted() == ADDRESS_IS_DELETED_TRUE) {
                throw new Exception("Can't find addressId");
            }
            if(updateAddressRequestDTO.getApartmentNumber().isEmpty()) {
                throw new Exception("Apartment number is required!");
            }
            if(updateAddressRequestDTO.getStreet().isEmpty()) {
                throw new Exception("Street is required!");
            }
            if(updateAddressRequestDTO.getWard().isEmpty()) {
                throw new Exception("Ward is required!");
            }
            if(updateAddressRequestDTO.getDistrict().isEmpty()) {
                throw new Exception("District is required!");
            }
            if(updateAddressRequestDTO.getProvince().isEmpty()) {
                throw new Exception("Province is required!");
            }
            else {
                address.get().setId(addressId);
                if(!address.get().getApartmentNumber().equals(updateAddressRequestDTO.getApartmentNumber())) {
                    address.get().setApartmentNumber(updateAddressRequestDTO.getApartmentNumber());
                }
                if(!address.get().getStreet().equals(updateAddressRequestDTO.getStreet())) {
                    address.get().setStreet(updateAddressRequestDTO.getStreet());
                }
                if(!address.get().getWard().equals(updateAddressRequestDTO.getWard())) {
                    address.get().setWard(updateAddressRequestDTO.getWard());
                }
                if(!address.get().getDistrict().equals(updateAddressRequestDTO.getDistrict())) {
                    address.get().setDistrict(updateAddressRequestDTO.getDistrict());
                }
                if(!address.get().getProvince().equals(updateAddressRequestDTO.getProvince())) {
                    address.get().setProvince(updateAddressRequestDTO.getProvince());
                }
                address.get().setUpdatedDate(new Date());

                addressRepository.save(address.get());

                return this.modelMapper.map(address.get(), UpdateAddressResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteAddress(Integer addressId) {

        try {
            this.addressRepository.findById(addressId)
                    .orElseThrow(() -> new IllegalArgumentException("AddressId is invalid!"));
            this.addressRepository.deleteById(addressId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteAddressTemporarily(Integer addressId) {

        try {
            Optional<Address> address = this.addressRepository.findById(addressId);
            if(address.isPresent() && address.get().getIsDeleted() == ADDRESS_IS_DELETED_FALSE) {
                address.get().setIsDeleted(ADDRESS_IS_DELETED_TRUE);
                this.addressRepository.save(address.get());
                return true;
            } else {
                throw new IllegalArgumentException("Can't find addressId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
