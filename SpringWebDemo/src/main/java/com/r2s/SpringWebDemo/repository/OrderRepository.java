package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Cart;
import com.r2s.SpringWebDemo.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.r2s.SpringWebDemo.constants.Constants.CartIsDelete0;
import static com.r2s.SpringWebDemo.constants.Constants.OrderIsDelete0;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findByIsDeleted(Boolean isDeleted);

    @Query(value = "SELECT ID, TOTAL_PRICE, TRANSPORTATION_FEE, CREATED_DATE, UPDATED_DATE, IS_DELETED " +
            "FROM \"ORDER\" " +
            "WHERE IS_DELETED = " + OrderIsDelete0 + " " +
            "ORDER BY ID " +
            "OFFSET (:size * (:page - 1)) ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Order> getAllOrder(@Param("page") Integer page, @Param("size") Integer size);
}
