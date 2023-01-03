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

    @GetMapping(value = "/get-all-cart")
    public ResponseEntity getAllCart(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "size", required = false) Integer size) {
        PagingResponseDTO pagingCartResponseDTO = new PagingResponseDTO();
        pagingCartResponseDTO.setResponseObjectList(cartService.getAllCart(page, size));
        pagingCartResponseDTO.setPage(page);
        pagingCartResponseDTO.setSize(size);

        return new ResponseEntity<>(pagingCartResponseDTO, HttpStatus.OK);
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
}
