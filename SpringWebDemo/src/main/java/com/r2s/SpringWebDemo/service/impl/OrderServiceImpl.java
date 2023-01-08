package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.OrderResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Order;
import com.r2s.SpringWebDemo.repository.CategoryRepository;
import com.r2s.SpringWebDemo.repository.OrderRepository;
import com.r2s.SpringWebDemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.r2s.SpringWebDemo.constants.Constants.CategoryIsDeleteFalse;
import static com.r2s.SpringWebDemo.constants.Constants.OrderIsDeleteFalse;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<OrderResponseDTO> getAllOrder(Integer page, Integer size) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        List<Order> orders = orderRepository.getAllOrder(page, size);
        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setTransportationFee(order.getTransportationFee());
            orderResponseDTO.setTotalPrice(order.getTotalPrice());
            orderResponseDTO.setId(order.getId());
            orderResponseDTOS.add(orderResponseDTO);
        }

        return orderResponseDTOS;
    }

    @Override
    public OrderResponseDTO getOrderById(Integer orderId) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if(order.isPresent() && order.get().getIsDeleted() == OrderIsDeleteFalse) {
                orderResponseDTO.setId(order.get().getId());
                orderResponseDTO.setTotalPrice(order.get().getTotalPrice());
                orderResponseDTO.setTransportationFee(order.get().getTransportationFee());
            } else {
                throw new NoSuchElementException("Can't find orderId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return orderResponseDTO;
    }

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        Order order = new Order();

        try {
            order.setTotalPrice(createOrderRequestDTO.getTotalPrice());
            order.setTransportationFee(createOrderRequestDTO.getTransportationFee());
            if(order.getCreatedDate() == null) {
                order.setCreatedDate(new Date());
            }
            if(order.getUpdatedDate() == null) {
                order.setUpdatedDate(new Date());
            }
            order.setIsDeleted(OrderIsDeleteFalse);
            orderResponseDTO.setId(order.getId());
            orderResponseDTO.setTotalPrice(order.getTotalPrice());
            orderResponseDTO.setTransportationFee(order.getTransportationFee());

            orderRepository.save(order);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return orderResponseDTO;
    }
}
