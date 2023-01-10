package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.service.CategoryService;
import com.r2s.SpringWebDemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/get-all")
    public ResponseEntity getAllOrder(Pageable pageable) {

        PagingResponseDTO pagingResponseDTO = this.orderService.getAllOrder(pageable);

        return new ResponseEntity<>(pagingResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{order-id}")
    public ResponseEntity getOrderById(@PathVariable("order-id") Integer orderId) {

        OrderResponseDTO orderResponseDTO = this.orderService.getOrderById(orderId);

        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO) {
        OrderResponseDTO orderResponseDTO = this.orderService.createOrder(createOrderRequestDTO);

        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@PathVariable("order-id") Integer orderId) {
        this.orderService.deleteOrderTemporarily(orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
