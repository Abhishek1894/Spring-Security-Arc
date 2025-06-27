package com.abhishek.demo.controller;

import com.abhishek.demo.model.Users;
import com.abhishek.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController
{
    @Autowired
    private UsersService service;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers()
    {
        List<Users> list = service.getUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user)
    {
        Users u = service.registerUser(user);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }
}
