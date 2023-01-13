package com.r2s.SpringWebDemo.repository;

import com.r2s.SpringWebDemo.entity.UserAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAddressRepository extends CrudRepository<UserAddress, Integer> {

   Iterable<UserAddress> findAllByUserId (Integer userId);
}
