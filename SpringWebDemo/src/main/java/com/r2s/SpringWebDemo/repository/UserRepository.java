package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Employer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Employer, Integer> {

    Optional<Page<Employer>> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

    Boolean existsByUsername(String username);

    Optional<Page<Category>> findAll(Pageable pageable);

    Employer findByUserName(String username);
}
