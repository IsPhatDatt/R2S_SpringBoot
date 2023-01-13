package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Address;
import com.r2s.SpringWebDemo.entity.Employer;
import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.entity.UserAddress;
import com.r2s.SpringWebDemo.repository.AddressRepository;
import com.r2s.SpringWebDemo.repository.UserRepository;
import com.r2s.SpringWebDemo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PagingResponseDTO getAllUser(Pageable pageable) {

        Page<Employer> userPage = this.userRepository.findAllByIsDeleted(USER_IS_DELETED_FALSE, pageable)
                .orElseThrow(() -> new RuntimeException("Can't get user by paging"));

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setPage(userPage.getNumber());
        pagingResponseDTO.setTotalPages(userPage.getTotalPages());
        pagingResponseDTO.setSize(userPage.getSize());
        pagingResponseDTO.setTotalRecords(userPage.getTotalElements());

        List<UserResponseDTO> userResponseDTOList = userPage.stream()
                .map((employer) -> this.modelMapper.map(employer, UserResponseDTO.class)).collect(Collectors.toList());

        pagingResponseDTO.setResponseObjectList(userResponseDTOList);

        return pagingResponseDTO;
    }

    @Override
    public UserResponseDTO getUserById(Integer userId) {

        try {
            Optional<Employer> user = this.userRepository.findById(userId);
            if(user.isPresent() && user.get().getIsDeleted() == USER_IS_DELETED_FALSE) {
                return this.modelMapper.map(user.get(), UserResponseDTO.class);
            } else {
                throw new NoSuchElementException("Can't find userId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {

        Employer employer = new Employer();

        try {
            if(createUserRequestDTO.getUsername().isEmpty()) {
                throw new Exception("Username is required!");
            }
            if(this.userRepository.existsByUsername(createUserRequestDTO.getUsername())) {
                throw new Exception("Product name is existed!");
            }
            if(createUserRequestDTO.getPassword().isEmpty()) {
                throw new Exception("Password is required!");
            }
            if(createUserRequestDTO.getFirstName().isEmpty()) {
                throw new Exception("First name is required!");
            }
            if(createUserRequestDTO.getLastName().isEmpty()) {
                throw new Exception("Last name is required!");
            } else {
                employer.setFirstName(createUserRequestDTO.getFirstName());
                employer.setLastName(createUserRequestDTO.getLastName());
                employer.setUsername(createUserRequestDTO.getUsername());
                employer.setPassword(createUserRequestDTO.getPassword());
                if(employer.getCreatedDate() == null) {
                    employer.setCreatedDate(new Date());
                }
                if(employer.getUpdatedDate() == null) {
                    employer.setUpdatedDate(new Date());
                }
                employer.setIsDeleted(USER_IS_DELETED_FALSE);

                this.userRepository.save(employer);

                return this.modelMapper.map(employer, UserResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public UpdateUserResponseDTO updateUser(Integer userId, UpdateUserRequestDTO updateUserRequestDTO) {

        Optional<Employer> user = this.userRepository.findById(userId);

        try {
            if(user.isEmpty() || user.get().getIsDeleted() == USER_IS_DELETED_TRUE) {
                throw new Exception("Can't find userId");
            }
            if(updateUserRequestDTO.getUsername().isEmpty()) {
                throw new Exception("Username is required!");
            }
            if(this.userRepository.existsByUsername(updateUserRequestDTO.getUsername())) {
                throw new Exception("Username is existed!");
            }
            else {
                user.get().setId(userId);
                if(!user.get().getUsername().equals(updateUserRequestDTO.getUsername())) {
                    user.get().setUsername(updateUserRequestDTO.getUsername());
                }
                if(user.get().getFirstName() == null || !user.get().getFirstName().equals(updateUserRequestDTO.getFirstName())) {
                    user.get().setFirstName(updateUserRequestDTO.getFirstName());
                }
                if(user.get().getLastName() == null || !user.get().getLastName().equals(updateUserRequestDTO.getLastName())) {
                    user.get().setLastName(updateUserRequestDTO.getLastName());
                }
                user.get().setUpdatedDate(new Date());

                userRepository.save(user.get());

                return this.modelMapper.map(user.get(), UpdateUserResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteUser(Integer userId) {

        try {
            Optional<Employer> user = userRepository.findById(userId);
            if(user.isPresent()) {
                this.userRepository.deleteById(userId);
                return true;
            } else {
                throw new NoSuchElementException("Can't find userId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteUserTemporarily(Integer userId) {
        try {
            Optional<Employer> user = this.userRepository.findById(userId);
            if(user.isPresent() && user.get().getIsDeleted() == USER_IS_DELETED_FALSE) {
                user.get().setIsDeleted(USER_IS_DELETED_TRUE);
                this.userRepository.save(user.get());
                return true;
            } else {
                throw new IllegalArgumentException("Can't find userId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductOfUserResponseDTO getProductByUserId(Integer userId) {

        if(!this.userRepository.existsById(userId)) {
            throw new IllegalArgumentException("UserId is invalid!");
        } else {
            Employer employer = this.userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Can't find userId!"));
            if(!employer.getIsDeleted() == USER_IS_DELETED_FALSE) {
                throw new RuntimeException("The employer is unavailable!");
            } else {
                Set<Product> products = employer.getProducts();
                Set<ProductResponseDTO> productResponseDTOSet = products.stream()
                        .map((product) -> this.modelMapper.map(product, ProductResponseDTO.class))
                        .collect(Collectors.toSet());

                ProductOfUserResponseDTO productOfUserResponseDTO = this.modelMapper.map(employer, ProductOfUserResponseDTO.class);
                productOfUserResponseDTO.setProducts(productResponseDTOSet);
                return productOfUserResponseDTO;
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AddressOfUserResponseDTO getAddressByUserId(Integer userId) {

        if(!this.userRepository.existsById(userId)) {
            throw new IllegalArgumentException("UserId is invalid!");
        } else {
            Employer employer = this.userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Can't find userId!"));
            if(!employer.getIsDeleted() == USER_IS_DELETED_FALSE) {
                throw new RuntimeException("The employer is unavailable!");
            } else {
                Set<UserAddress> userAddresses = employer.getAddresses();
                Set<UserAddressResponseDTO> userAddressResponseDTOSet = userAddresses.stream()
                        .map((userAddress) -> this.modelMapper.map(userAddress, UserAddressResponseDTO.class))
                        .collect(Collectors.toSet());

                Set<Address> addressSet = new HashSet<>();

                for(UserAddressResponseDTO userAddressResponseDTO : userAddressResponseDTOSet) {
                    addressSet.add(userAddressResponseDTO.getAddressId());
                }

                Set<AddressResponseDTO> addressResponseDTOSet = addressSet.stream()
                        .map((address) -> this.modelMapper.map(address, AddressResponseDTO.class))
                        .collect(Collectors.toSet());

                AddressOfUserResponseDTO addressOfUserResponseDTO = this.modelMapper.map(employer, AddressOfUserResponseDTO.class);
                addressOfUserResponseDTO.setAddressResponseDTOSet(addressResponseDTOSet);
                return addressOfUserResponseDTO;
            }
        }
    }
}
