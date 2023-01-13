package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.r2s.SpringWebDemo.constants.Constants.*;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Page<Product>> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

    Boolean existsByName(String name);

    Optional<Page<Product>> findAll(Pageable pageable);
}
