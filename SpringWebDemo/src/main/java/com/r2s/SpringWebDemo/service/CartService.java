package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.CreateCartRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CartResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateUserResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UserResponseDTO;

import java.util.List;

public interface CartService {
    List<CartResponseDTO> getAllCart(Integer page, Integer size);

    CartResponseDTO getCartById(Integer cartId);

    CartResponseDTO createCart(CreateCartRequestDTO createCartRequestDTO);

}
