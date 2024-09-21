package com.example.demo.service;

import com.example.demo.dto.AddUserRequest;
import com.example.demo.jwt.JwtUser;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    JwtUser loadUserByUsername(String username);

    Boolean addUser(AddUserRequest request);
}
