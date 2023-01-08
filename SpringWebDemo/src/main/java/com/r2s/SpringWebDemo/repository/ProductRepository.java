package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.r2s.SpringWebDemo.constants.Constants.*;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByIsDeleted(Boolean isDeleted);

    Boolean existsByName(String name);

    @Query(value = "SELECT ID, NAME, PRICE, QUANTITY, CREATED_DATE, UPDATED_DATE, IS_DELETED " +
            "FROM PRODUCT " +
            "WHERE IS_DELETED = " + ProductIsDelete0 + " " +
            "ORDER BY ID " +
            "OFFSET (:size * (:page - 1)) ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Product> getAllProduct(@Param("page") Integer page, @Param("size") Integer size);
}
