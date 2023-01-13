package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateCartRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CartResponseDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.PagingResponseDTO;
import com.r2s.SpringWebDemo.entity.Cart;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.repository.CartRepository;
import com.r2s.SpringWebDemo.service.CartService;
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
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PagingResponseDTO getAllCart(Pageable pageable) {

        Page<Cart> cartPage = this.cartRepository.findAllByIsDeleted(CART_IS_DELETED_FALSE, pageable)
                .orElseThrow(() -> new RuntimeException("Can't get cart by paging"));

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setPage(cartPage.getNumber());
        pagingResponseDTO.setTotalPages(cartPage.getTotalPages());
        pagingResponseDTO.setSize(cartPage.getSize());
        pagingResponseDTO.setTotalRecords(cartPage.getTotalElements());

        List<CartResponseDTO> cartResponseDTOList = cartPage.stream()
                .map((cart) -> this.modelMapper.map(cart, CartResponseDTO.class)).collect(Collectors.toList());

        pagingResponseDTO.setResponseObjectList(cartResponseDTOList);

        return pagingResponseDTO;
    }

    @Override
    public CartResponseDTO getCartById(Integer cartId) {

        try {
            Optional<Cart> cart = this.cartRepository.findById(cartId);
            if(cart.isPresent() && cart.get().getIsDeleted() == CART_IS_DELETED_FALSE) {
                return this.modelMapper.map(cart.get(), CartResponseDTO.class);
            } else {
                throw new NoSuchElementException("Can't find cartId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public CartResponseDTO createCart(CreateCartRequestDTO createCartRequestDTO) {

        Cart cart = new Cart();

        try {
            if(createCartRequestDTO.getTotalPrice() == null) {
                throw new Exception("Cart total price is not null!");
            }
            else {
                cart.setTotalPrice(createCartRequestDTO.getTotalPrice());
                if(cart.getCreatedDate() == null) {
                    cart.setCreatedDate(new Date());
                }
                if(cart.getUpdatedDate() == null) {
                    cart.setUpdatedDate(new Date());
                }
                cart.setIsDeleted(CART_IS_DELETED_FALSE);

                this.cartRepository.save(cart);

                return this.modelMapper.map(cart, CartResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, IllegalArgumentException.class, Throwable.class})
    public Boolean deleteCart(Integer cartId) {

        try {
            this.cartRepository.findById(cartId)
                    .orElseThrow(() -> new IllegalArgumentException("CartId is invalid!"));
            this.cartRepository.deleteById(cartId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteCartTemporarily(Integer cartId) {

        try {
            Optional<Cart> cart = this.cartRepository.findById(cartId);
            if(cart.isPresent() && cart.get().getIsDeleted() == CART_IS_DELETED_FALSE) {
                cart.get().setIsDeleted(CART_IS_DELETED_TRUE);
                this.cartRepository.save(cart.get());
                return true;
            } else {
                throw new IllegalArgumentException("Can't find cartId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
