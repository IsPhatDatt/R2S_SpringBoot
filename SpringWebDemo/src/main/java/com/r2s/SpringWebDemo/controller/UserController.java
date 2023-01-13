package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.service.CategoryService;
import com.r2s.SpringWebDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/get-all")
    public ResponseEntity getAllUser(Pageable pageable) {

        PagingResponseDTO pagingResponseDTO = userService.getAllUser(pageable);

        return new ResponseEntity<>(pagingResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{user-id}")
    public ResponseEntity getUserById(@PathVariable("user-id") Integer userId) {
        UserResponseDTO userResponseDTO = this.userService.getUserById(userId);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {

        UserResponseDTO userResponseDTO = this.userService.createUser(createUserRequestDTO);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{user-id}")
    public ResponseEntity updateUser(@PathVariable("user-id") Integer userId,
                                     @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        UpdateUserResponseDTO response = this.userService.updateUser(userId, updateUserRequestDTO);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") Integer userId) {
        this.userService.deleteUserTemporarily(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{user-id}/products")
    public ResponseEntity getProductByUserId(@PathVariable("user-id") Integer userId) {
        ProductOfUserResponseDTO productOfUserResponseDTO = this.userService.getProductByUserId(userId);

        return new ResponseEntity<>(productOfUserResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/addresses")
    public ResponseEntity getAddressByUserId(@PathVariable("user-id") Integer userId) {
        AddressOfUserResponseDTO addressOfUserResponseDTO = this.userService.getAddressByUserId(userId);

        return new ResponseEntity<>(addressOfUserResponseDTO, HttpStatus.OK);
    }
}
