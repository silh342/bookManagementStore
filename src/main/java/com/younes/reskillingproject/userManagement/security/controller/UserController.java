package com.younes.reskillingproject.userManagement.security.controller;


import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import com.younes.reskillingproject.userManagement.security.dto.AuthenticationResponse;
import com.younes.reskillingproject.userManagement.security.dto.LoginRequest;
import com.younes.reskillingproject.userManagement.security.dto.SetUserRoleRequestBody;
import com.younes.reskillingproject.userManagement.security.dto.UserRequestBody;
import com.younes.reskillingproject.userManagement.security.entity.Role;
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.jwtAuthentication.JwtGenerator;
import com.younes.reskillingproject.userManagement.security.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
@Tag(name = "Authentication Endpoints")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public UserController(UserServiceImpl userService, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          JwtGenerator jwtGenerator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
    }


    @Operation(summary = "Create a new role endpoint")
    @PostMapping("/role")
    @Hidden
    public Role saveRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }
    @PostMapping("/validatetoken")
    @Hidden
    public boolean checkTokenValidity(@RequestBody String token) {
        return jwtGenerator.validateToken(token);
    }
    @Operation(summary = "Register a new user in database")
    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody UserRequestBody user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userService.addUser(new UserRequestBody(user.getUsername(), encodedPassword,user.getEmail(), user.getRoleNames()));
        return new ResponseEntity<>("User created successfully!", HttpStatus.OK);
    }
    @Operation(summary = "Log a user")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginInfo) {
        return userService.authenticate(loginInfo);
    }
    @Operation(summary = "Attribute a role to a given user")
    @PostMapping("/setuserrole")
    public User setUserRole(@RequestBody SetUserRoleRequestBody userRoleInfo) {
        return userService.setUserRole(userRoleInfo.getUsername(), userRoleInfo.getRolename());
    }
    @Operation(summary = "Get list of users available")
    @GetMapping("getusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
