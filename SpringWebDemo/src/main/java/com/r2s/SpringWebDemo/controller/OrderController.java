package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @GetMapping(value = "/get-all-order")
    public ResponseEntity getAllOrder(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size,
                                        @RequestParam(value = "sort", required = false) String sort) {
        OrderResponseDTO virtualOrder1 = new OrderResponseDTO(1, 35000.0, 150000.0);
        OrderResponseDTO virtualOrder2 = new OrderResponseDTO(2, 35000.0, 150000.0);
        OrderResponseDTO virtualOrder3 = new OrderResponseDTO(3, 35000.0, 150000.0);
        OrderResponseDTO virtualOrder4 = new OrderResponseDTO(4, 35000.0, 150000.0);
        OrderResponseDTO virtualOrder5 = new OrderResponseDTO(5, 35000.0, 150000.0);


        List<OrderResponseDTO> listOrderResponseDTO = new ArrayList<>();
        listOrderResponseDTO.add(virtualOrder1);
        listOrderResponseDTO.add(virtualOrder2);
        listOrderResponseDTO.add(virtualOrder3);
        listOrderResponseDTO.add(virtualOrder4);
        listOrderResponseDTO.add(virtualOrder5);

        PagingResponseDTO pagingOrderResponseDTO = new PagingResponseDTO();
        pagingOrderResponseDTO.setResponseObjectList(listOrderResponseDTO);
        pagingOrderResponseDTO.setPage(page);
        pagingOrderResponseDTO.setSize(size);
        pagingOrderResponseDTO.setSort(sort);

        return new ResponseEntity<>(pagingOrderResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{order-id}")
    public ResponseEntity getOrderById(@PathVariable("order-id") Integer orderId) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(orderId, 25000.0 , 3000000.0);

        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setTransportationFee(createOrderRequestDTO.getTransportationFee());
        orderResponseDTO.setTotalPrice(createOrderRequestDTO.getTotalPrice());

        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{order-id}")
    public ResponseEntity updateOrder(@PathVariable("order-id") Integer orderId,
                                        @RequestBody UpdateOrderRequestDTO updateOrderRequestDTO) {
        UpdateOrderResponseDTO response = new UpdateOrderResponseDTO();
        response.setId(orderId);
        response.setTransportationFee(updateOrderRequestDTO.getTransportationFee());
        response.setTotalPrice(updateOrderRequestDTO.getTotalPrice());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@PathVariable("order-id") String orderId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
