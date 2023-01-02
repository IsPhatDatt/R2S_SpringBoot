package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
