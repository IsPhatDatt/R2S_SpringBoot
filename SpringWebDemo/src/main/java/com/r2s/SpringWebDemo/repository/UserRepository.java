package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.r2s.SpringWebDemo.constants.Constants.ProductIsDelete0;
import static com.r2s.SpringWebDemo.constants.Constants.UserIsDelete0;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByIsDeleted(Boolean isDeleted);

    Boolean existsByUsername(String username);

    @Query(value = "SELECT ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, CREATED_DATE, UPDATED_DATE, IS_DELETED " +
            "FROM \"USER\" " +
            "WHERE IS_DELETED = " + UserIsDelete0 + " " +
            "ORDER BY ID " +
            "OFFSET (:size * (:page - 1)) ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<User> getAllUser(@Param("page") Integer page, @Param("size") Integer size);
}
