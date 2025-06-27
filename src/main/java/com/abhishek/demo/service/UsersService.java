package com.abhishek.demo.service;

import com.abhishek.demo.model.Users;
import com.abhishek.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService
{
    @Autowired
    private UserRepository repository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<Users> getUsers()
    {
        return repository.findAll();
    }

    public Users registerUser(Users user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

    public String verify(Users user)
    {
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());

        return "Failure";
    }
}
