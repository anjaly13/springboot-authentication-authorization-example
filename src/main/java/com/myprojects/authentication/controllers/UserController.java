package com.myprojects.authentication.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.myprojects.authentication.security.SecurityConstants;
import com.myprojects.authentication.services.users.UserServiceImpl;
import com.myprojects.authentication.storage.users.ApplicationUser;
import com.myprojects.authentication.storage.users.ApplicationUserRepository;
import com.myprojects.authentication.storage.users.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Controller
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private UserServiceImpl userService;




    @GetMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){

        String token = null;
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(loginRequest.getUsername());

        if (bCryptPasswordEncoder.matches(loginRequest.getPassword(),applicationUser.getPassword())){
            System.out.println("Login successful");

            // always save subject in encrypted format
            token =  JWT.create()
                    .withSubject(applicationUser.getUsername()+"-"+applicationUser.getRole())
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        }
        return new ResponseEntity(token, HttpStatus.OK);
    }
    @GetMapping("/display")
    public ResponseEntity display(){
        return new ResponseEntity("display labels",HttpStatus.OK);
    }
}
