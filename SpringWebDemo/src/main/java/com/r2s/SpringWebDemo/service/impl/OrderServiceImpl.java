package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateOrderRequestDTO;
import com.r2s.SpringWebDemo.dto.response.OrderResponseDTO;
import com.r2s.SpringWebDemo.dto.response.PagingResponseDTO;
import com.r2s.SpringWebDemo.entity.Bill;
import com.r2s.SpringWebDemo.repository.OrderRepository;
import com.r2s.SpringWebDemo.service.OrderService;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PagingResponseDTO getAllOrder(Pageable pageable) {

        Page<Bill> orderPage = this.orderRepository.findAllByIsDeleted(ORDER_IS_DELETED_FALSE, pageable)
                .orElseThrow(() -> new RuntimeException("Can't get order by paging"));

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setPage(orderPage.getNumber());
        pagingResponseDTO.setTotalPages(orderPage.getTotalPages());
        pagingResponseDTO.setSize(orderPage.getSize());
        pagingResponseDTO.setTotalRecords(orderPage.getTotalElements());

        List<OrderResponseDTO> orderResponseDTOS = orderPage.stream()
                .map((bill) -> this.modelMapper.map(bill, OrderResponseDTO.class)).collect(Collectors.toList());

        pagingResponseDTO.setResponseObjectList(orderResponseDTOS);

        return pagingResponseDTO;
    }

    @Override
    public OrderResponseDTO getOrderById(Integer orderId) {

        try {
            Optional<Bill> order = this.orderRepository.findById(orderId);
            if(order.isPresent() && order.get().getIsDeleted() == ORDER_IS_DELETED_FALSE) {
                return this.modelMapper.map(order.get(), OrderResponseDTO.class);
            } else {
                throw new NoSuchElementException("Can't find orderId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {

        Bill bill = new Bill();

        try {
            if(createOrderRequestDTO.getTotalPrice() == null) {
                throw new Exception("Bill total price is not null!");
            }
            if(createOrderRequestDTO.getTransportationFee() == null) {
                throw new Exception("Bill transportation fee is not null!");
            }
            else {
                bill.setTotalPrice(createOrderRequestDTO.getTotalPrice());
                bill.setTransportationFee(createOrderRequestDTO.getTransportationFee());
                if(bill.getCreatedDate() == null) {
                    bill.setCreatedDate(new Date());
                }
                if(bill.getUpdatedDate() == null) {
                    bill.setUpdatedDate(new Date());
                }
                bill.setIsDeleted(ORDER_IS_DELETED_FALSE);

                this.orderRepository.save(bill);

                return this.modelMapper.map(bill, OrderResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteOrder(Integer orderId) {

        try {
            this.orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("OrderId is invalid!"));
            this.orderRepository.deleteById(orderId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteOrderTemporarily(Integer orderId) {

        try {
            Optional<Bill> order = this.orderRepository.findById(orderId);
            if(order.isPresent() && order.get().getIsDeleted() == ORDER_IS_DELETED_FALSE) {
                order.get().setIsDeleted(ORDER_IS_DELETED_TRUE);
                this.orderRepository.save(order.get());
                return true;
            } else {
                throw new IllegalArgumentException("Can't find orderId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
