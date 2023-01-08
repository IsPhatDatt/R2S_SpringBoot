package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> getAllOrder(Integer page, Integer size);

    OrderResponseDTO getOrderById(Integer orderId);

    OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO);
}
