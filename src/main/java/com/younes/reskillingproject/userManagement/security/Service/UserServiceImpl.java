package com.younes.reskillingproject.userManagement.security.Service;

import com.younes.reskillingproject.userManagement.security.dto.AuthenticationResponse;
import com.younes.reskillingproject.userManagement.security.dto.CustomUserDetails;
import com.younes.reskillingproject.userManagement.security.dto.LoginRequest;
import com.younes.reskillingproject.userManagement.security.dto.UserRequestBody;
import com.younes.reskillingproject.userManagement.security.entity.Role;
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.error.EmailExistsException;
import com.younes.reskillingproject.userManagement.security.error.UsernameExistsException;
import com.younes.reskillingproject.userManagement.security.jwtAuthentication.JwtGenerator;
import com.younes.reskillingproject.userManagement.security.repository.RoleRepository;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {

    public UserRepository userRepository;
    public RoleRepository roleRepository;
    public AuthenticationManager authenticationManager;
    public JwtGenerator jwtGenerator;

    @Autowired public UserServiceImpl(@Lazy UserRepository userRepository, @Lazy RoleRepository roleRepository,
                                      @Lazy AuthenticationManager authenticationManager,
                                      @Lazy JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getUserRoles()), user.getEmail());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<AuthenticationResponse> authenticate(LoginRequest userInfo) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }



    public void addUser(UserRequestBody newUser) {

        if(userRepository.existsByUsername(newUser.getUsername().toLowerCase())) {
            throw new UsernameExistsException("Username " + newUser.getUsername() + " Already exists, choose another one");
        }
        if(userRepository.existsByEmail(newUser.getEmail().toLowerCase())) {
            throw new EmailExistsException("An account already exists with this email, please log in or try another email");
        }
        Set<Role> ListRoles = new HashSet<>();
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());

        for(String name : newUser.getRoleNames()) {
            Role role = roleRepository.findRoleByName(name);
            if(role != null) ListRoles.add(role);
        }
        user.setUserRoles(ListRoles);
        userRepository.save(user);
    }

    public User setUserRole(String username, String rolename) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findRoleByName(rolename);
        user.getUserRoles().clear();
        user.getUserRoles().add(role);
        return userRepository.save(user);
    }
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
