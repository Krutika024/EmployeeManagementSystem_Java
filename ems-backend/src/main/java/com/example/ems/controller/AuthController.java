package com.example.ems.controller;

import com.example.ems.entity.Employee;
import com.example.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public Object register(@RequestBody Employee e){
        return service.register(e);
    }

    @PostMapping("/login")
    public Object login(@RequestBody Map<String,String> data){
        return service.login(data);
    }
}

