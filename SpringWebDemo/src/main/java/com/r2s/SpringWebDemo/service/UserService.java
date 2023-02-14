package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.*;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Employer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    PagingResponseDTO getAllUser(Pageable pageable);

    UserResponseDTO getUserById(Integer userId);

    UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);

    UpdateUserResponseDTO updateUser(Integer userId, UpdateUserRequestDTO updateUserRequestDTO);

    Boolean deleteUser(Integer userId);

    Boolean deleteUserTemporarily(Integer userId);

    ProductOfUserResponseDTO getProductByUserId(Integer userId);

    AddressOfUserResponseDTO getAddressByUserId(Integer userId);

    String login(LoginRequest authenticationRequest);

    Employer register(RegisterRequest registerRequest);
}
