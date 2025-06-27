package com.abhishek.demo.service;


import com.abhishek.demo.model.UserPrincipal;
import com.abhishek.demo.model.Users;
import com.abhishek.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Users user = repository.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("user not found");

        return new UserPrincipal(user);
    }
}
