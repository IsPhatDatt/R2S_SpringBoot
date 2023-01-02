package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Address;
import com.r2s.SpringWebDemo.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.r2s.SpringWebDemo.constants.Constants.ProductIsDelete0;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    List<Address> findByIsDeleted(Boolean isDeleted);

//    Boolean existsByName(String name);

    @Query(value = "SELECT ID, ADDRESS, CREATED_DATE, UPDATED_DATE, IS_DELETED " +
            "FROM ADDRESS " +
            "WHERE IS_DELETED = " + ProductIsDelete0 + " " +
            "ORDER BY ID " +
            "OFFSET (:size * (:page - 1)) ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Address> getAllAddress(@Param("page") Integer page, @Param("size") Integer size);
}
