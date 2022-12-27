package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(value = "user")
public class UserController {
    @GetMapping
    public ResponseEntity getAllUser() {
        String response = new String("Get All User");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity insertUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        String firstName = createUserRequestDTO.getFirstName();
        String lastName = createUserRequestDTO.getLastName();
        String phone = createUserRequestDTO.getPhone();
        StringBuilder stringBuilder = new StringBuilder();
        String response = stringBuilder.append(firstName).append(lastName).append(phone).toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
