package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateCartRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CartResponseDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.entity.Cart;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.repository.CartRepository;
import com.r2s.SpringWebDemo.repository.CategoryRepository;
import com.r2s.SpringWebDemo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.r2s.SpringWebDemo.constants.Constants.CartIsDeleteFalse;
import static com.r2s.SpringWebDemo.constants.Constants.CategoryIsDeleteFalse;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<CartResponseDTO> getAllCart(Integer page, Integer size) {
        List<CartResponseDTO> cartResponseDTOS = new ArrayList<>();
        List<Cart> carts = cartRepository.getAllCart(page, size);
        for (Cart cart : carts) {
            CartResponseDTO cartResponseDTO = new CartResponseDTO();
            cartResponseDTO.setTotalPrice(cart.getTotalPrice());
            cartResponseDTO.setId(cart.getId());
            cartResponseDTO.setCreatedDate(cart.getCreatedDate());
            cartResponseDTOS.add(cartResponseDTO);
        }

        return cartResponseDTOS;
    }

    @Override
    public CartResponseDTO getCartById(Integer cartId) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        try {
            Optional<Cart> cart = cartRepository.findById(cartId);
            if(cart.isPresent() && cart.get().getIsDeleted() == CartIsDeleteFalse) {
                cartResponseDTO.setId(cart.get().getId());
                cartResponseDTO.setTotalPrice(cart.get().getTotalPrice());
                cartResponseDTO.setCreatedDate(cart.get().getCreatedDate());
            } else {
                throw new NoSuchElementException("Can't find cartId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return cartResponseDTO;
    }

    @Override
    public CartResponseDTO createCart(CreateCartRequestDTO createCartRequestDTO) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        Cart cart = new Cart();

        try {
            cart.setTotalPrice(createCartRequestDTO.getTotalPrice());
            if(cart.getCreatedDate() == null) {
                cart.setCreatedDate(new Date());
            }
            if(cart.getUpdatedDate() == null) {
                cart.setUpdatedDate(new Date());
            }
            cart.setIsDeleted(CartIsDeleteFalse);
            cartResponseDTO.setId(cart.getId());
            cartResponseDTO.setTotalPrice(cart.getTotalPrice());
            cartResponseDTO.setCreatedDate(cart.getCreatedDate());

            cartRepository.save(cart);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return cartResponseDTO;
    }
}
