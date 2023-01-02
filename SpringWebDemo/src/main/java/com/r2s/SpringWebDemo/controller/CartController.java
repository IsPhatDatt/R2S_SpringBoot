package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {
    @GetMapping(value = "/get-all-cart")
    public ResponseEntity getAllCart(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "size", required = false) Integer size,
                                      @RequestParam(value = "sort", required = false) String sort) {
        CartResponseDTO virtualCart1 = new CartResponseDTO(1, new Date(2022,12,20) , 150000.0, null);
        CartResponseDTO virtualCart2 = new CartResponseDTO(2, new Date(2022,12,20) , 150000.0, null);
        CartResponseDTO virtualCart3 = new CartResponseDTO(3, new Date(2022,12,20) , 150000.0, null);
        CartResponseDTO virtualCart4 = new CartResponseDTO(4, new Date(2022,12,20) , 150000.0, null);
        CartResponseDTO virtualCart5 = new CartResponseDTO(5, new Date(2022,12,20) , 150000.0, null);

        List<CartResponseDTO> listCartResponseDTO = new ArrayList<>();

        PagingResponseDTO pagingCartResponseDTO = new PagingResponseDTO();
        pagingCartResponseDTO.setResponseObjectList(listCartResponseDTO);
        pagingCartResponseDTO.setPage(page);
        pagingCartResponseDTO.setSize(size);
        pagingCartResponseDTO.setSort(sort);

        return new ResponseEntity<>(pagingCartResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{cart-id}")
    public ResponseEntity getCartById(@PathVariable("cart-id") Integer cartId) {
        CartItemResponseDTO virtualCartItem1 = new CartItemResponseDTO(1, 1, 3);
        CartItemResponseDTO virtualCartItem2 = new CartItemResponseDTO(2, 2, 3);
        CartItemResponseDTO virtualCartItem3 = new CartItemResponseDTO(3, 3, 3);
        CartItemResponseDTO virtualCartItem4 = new CartItemResponseDTO(4, 4, 3);
        CartItemResponseDTO virtualCartItem5 = new CartItemResponseDTO(5, 5, 3);


        List<CartItemResponseDTO> listCartItemResponseDTO = new ArrayList<>();
        listCartItemResponseDTO.add(virtualCartItem1);
        listCartItemResponseDTO.add(virtualCartItem2);
        listCartItemResponseDTO.add(virtualCartItem3);
        listCartItemResponseDTO.add(virtualCartItem4);
        listCartItemResponseDTO.add(virtualCartItem5);

        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setId(cartId);
        cartResponseDTO.setTotalPrice(150000.0);
        cartResponseDTO.setCreatedDate(new Date(2022, 12, 29));
        cartResponseDTO.setListCartLineItemResponseDTO(listCartItemResponseDTO);

        return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
    }
}
