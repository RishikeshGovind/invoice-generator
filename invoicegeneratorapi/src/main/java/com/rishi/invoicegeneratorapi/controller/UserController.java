package com.rishi.invoicegeneratorapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rishi.invoicegeneratorapi.entity.User;
import com.rishi.invoicegeneratorapi.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public User createOrUpdateUser(@RequestBody User user, Authentication authentication){
        try{
            if(!authentication.getName().equals(user.getClerkId())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to access this resource");
            }
            // Log, save to DB, etc.
            System.out.println("User received: " + user);
            return userService.saveOrUpdateUser(user);
        } catch (Exception e){
            throw new RuntimeException(e);

        }
        
    }
    

}
