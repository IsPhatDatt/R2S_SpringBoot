package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.service.CategoryService;
import com.r2s.SpringWebDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/get-all-user")
    public ResponseEntity getAllUser(@RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "size", required = false) Integer size) {
        PagingResponseDTO pagingUserResponseDTO = new PagingResponseDTO();
        pagingUserResponseDTO.setResponseObjectList(userService.getAllUser(page, size));
        pagingUserResponseDTO.setPage(page);
        pagingUserResponseDTO.setSize(size);

        return new ResponseEntity<>(pagingUserResponseDTO, HttpStatus.OK);
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
        this.userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
