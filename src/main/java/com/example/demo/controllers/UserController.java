package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.splunk.Args;
import com.splunk.Receiver;
import com.splunk.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        logger.info("Getting user by username :: " + username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        logger.info("Cart created for new user :: " + createUserRequest.getUsername());

        // matching password and confirm password
        if (createUserRequest.getPassword().length() > 7 && createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            user.setCart(cart);
            user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
            userRepository.save(user);
            logger.info("New user created with username :: " + createUserRequest.getUsername());

            return ResponseEntity.ok(user);
        } else {
            logger.info("Password is short ( < 7) for new user request :: " + createUserRequest.getUsername());

            return ResponseEntity.badRequest().build();
        }
    }

}
