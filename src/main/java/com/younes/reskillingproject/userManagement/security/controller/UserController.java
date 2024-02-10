package com.younes.reskillingproject.userManagement.security.controller;


import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import com.younes.reskillingproject.userManagement.security.model.Role;
import com.younes.reskillingproject.userManagement.security.model.UserRequestBody;
import com.younes.reskillingproject.userManagement.security.repository.RoleRepository;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserServiceImpl userService, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/role")
    public Role saveRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }
    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody UserRequestBody user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("The username already exists, please provide a different username",
                    HttpStatus.CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userService.addUser(new UserRequestBody(user.getUsername(), encodedPassword, user.getRoleNames()));
        return new ResponseEntity<>("User created successfully!", HttpStatus.ACCEPTED);
    }



}
