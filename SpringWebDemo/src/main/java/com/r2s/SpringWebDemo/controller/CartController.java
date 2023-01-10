package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateCartRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Cart;
import com.r2s.SpringWebDemo.service.CartService;
import com.r2s.SpringWebDemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping(value = "/get-all")
    public ResponseEntity getAllCart(Pageable pageable) {

        PagingResponseDTO pagingResponseDTO = cartService.getAllCart(pageable);

        return new ResponseEntity<>(pagingResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{cart-id}")
    public ResponseEntity getCartById(@PathVariable("cart-id") Integer cartId) {

        CartResponseDTO cartResponseDTO = this.cartService.getCartById(cartId);

        return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertCart(@RequestBody CreateCartRequestDTO createCartRequestDTO) {

        CartResponseDTO cartResponseDTO = this.cartService.createCart(createCartRequestDTO);

        return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{cart-id}")
    public ResponseEntity deleteCart(@PathVariable("cart-id") Integer cartId) {
        this.cartService.deleteCartTemporarily(cartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
