package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.CreateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.ProductResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateProductResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateUserResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUser(Integer page, Integer size);

    UserResponseDTO getUserById(Integer userId);

    UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);

    UpdateUserResponseDTO updateUser(Integer userId, UpdateUserRequestDTO updateUserRequestDTO);

    Boolean deleteUser(Integer userId);
}
