package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> getUsers(){
        return userDao.findAll();
    }

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable Long id){
       User user=null;
       user = userDao.findByUserById(id);
        return user;
    }




}
