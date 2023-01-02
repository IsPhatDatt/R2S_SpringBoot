package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Category;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.r2s.SpringWebDemo.constants.Constants.*;
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    List<Category> findByIsDeleted(Boolean isDeleted);
    Boolean existsByName(String name);

    @Query(value = "SELECT ID, NAME, CREATED_DATE, UPDATED_DATE, IS_DELETED " +
            "FROM CATEGORY " +
            "WHERE IS_DELETED = " + CategoryIsDelete0 + " " +
            "ORDER BY ID " +
            "OFFSET (:size * (:page - 1)) ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Category> getAllCategory(@Param("page") Integer page, @Param("size") Integer size);
}
