package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Cart;
import com.r2s.SpringWebDemo.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.r2s.SpringWebDemo.constants.Constants.CartIsDelete0;
import static com.r2s.SpringWebDemo.constants.Constants.CategoryIsDelete0;

public interface CartRepository extends CrudRepository<Cart, Integer> {

    List<Cart> findByIsDeleted(Boolean isDeleted);

    @Query(value = "SELECT ID, TOTAL_PRICE, CREATED_DATE, UPDATED_DATE, IS_DELETED " +
            "FROM CART " +
            "WHERE IS_DELETED = " + CartIsDelete0 + " " +
            "ORDER BY ID " +
            "OFFSET (:size * (:page - 1)) ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Cart> getAllCart(@Param("page") Integer page, @Param("size") Integer size);

}
