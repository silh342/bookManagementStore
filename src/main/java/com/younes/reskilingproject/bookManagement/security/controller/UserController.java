package com.younes.reskilingproject.bookManagement.security.controller;


import com.younes.reskilingproject.bookManagement.security.Service.UserServiceImpl;
import com.younes.reskilingproject.bookManagement.security.model.Role;
import com.younes.reskilingproject.bookManagement.security.model.User;
import com.younes.reskilingproject.bookManagement.security.model.UserRequestBody;
import com.younes.reskilingproject.bookManagement.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserServiceImpl userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody UserRequestBody user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        return userService.addUser(new UserRequestBody(user.getUsername(), encodedPassword, user.getRoleNames()));
    }

    @PostMapping("/role")
    public Role saveRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

}
