package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.config.JwtTokenUtil;
import com.r2s.SpringWebDemo.dto.request.LoginRequest;
import com.r2s.SpringWebDemo.dto.request.RegisterRequest;
import com.r2s.SpringWebDemo.dto.response.JwtResponse;
import com.r2s.SpringWebDemo.entity.Employer;
import com.r2s.SpringWebDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

        String token = userService.login(authenticationRequest);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        Employer user = userService.register(registerRequest);
        return ResponseEntity.ok(user);
    }

}
