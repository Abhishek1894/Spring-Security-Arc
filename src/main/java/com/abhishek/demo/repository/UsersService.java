package com.abhishek.demo.repository;

import com.abhishek.demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService
{
    @Autowired
    private UserRepository repository;

    public List<Users> getUsers()
    {
        return repository.findAll();
    }

    public Users registerUser(Users user)
    {
        repository.save(user);
        return user;
    }
}
