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
//        StringBuilder response = new StringBuilder("Update User With Info: ");
//        response.append("user-id: ").append(userId);
//        response.append("name: ").append(updateUserRequestDTO.getName());
//        response.append("email: ").append(updateUserRequestDTO.getEmail());
//        UpdateUserResponseDTO response = new UpdateUserResponseDTO();
//        response.setId(userId);
//        response.setFirstName(updateUserRequestDTO.getFirstName());
//        response.setLastName(updateUserRequestDTO.getLastName());
//        response.setUsername(updateUserRequestDTO.getUsername());
//
//        UpdateUserListResponseDTO responseListUserDTO = new UpdateUserListResponseDTO();
//        responseListUserDTO.setResponseDTOList(Collections.singletonList(response));
        //Hàm Collections.singletonList(response) có nghĩa là trong list chỉ có 1 phần tử duy nhất là response
        UpdateUserResponseDTO response = this.userService.updateUser(userId, updateUserRequestDTO);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") Integer userId) {
//        return ResponseEntity.ok("Delete success!");
        this.userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    @GetMapping(value = "/{user-id}")
//    public ResponseEntity getAllUser(@PathVariable("user-id") String userId,
//                                     @RequestParam(value = "page", required = false) Integer page,
//                                     @RequestParam(value = "size", required = false) Integer size,
//                                     @RequestParam(value = "sort", required = false) String sort) {
//        StringBuilder response = new StringBuilder("Get All User: ");
//        response.append("user-id: ").append(userId);
//        response.append("page: ").append(page);
//        response.append("size: ").append(size);
//        response.append("sort: ").append(sort);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @PostMapping
//    public ResponseEntity insertUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
//        String firstName = createUserRequestDTO.getFirstName();
//        String lastName = createUserRequestDTO.getLastName();
//        String phone = createUserRequestDTO.getPhone();
//        StringBuilder stringBuilder = new StringBuilder();
//        String response = stringBuilder.append(firstName).append(lastName).append(phone).toString();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
