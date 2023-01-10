package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.r2s.SpringWebDemo.constants.Constants.ORDER_IS_DELETED_0;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    Optional<Page<Order>> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

    Optional<Page<Order>> findAll(Pageable pageable);
}
