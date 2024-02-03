package com.younes.reskilingproject.bookManagement.security.Service;

import com.younes.reskilingproject.bookManagement.security.model.Role;
import com.younes.reskilingproject.bookManagement.security.model.User;
import com.younes.reskilingproject.bookManagement.security.repository.RoleRepository;
import com.younes.reskilingproject.bookManagement.security.repository.UserRepository;
import com.younes.reskilingproject.bookManagement.security.model.UserRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getUserRoles()));
    }

    public User addUser(UserRequestBody newUser) {
        Set<Role> ListRoles = new HashSet<>();
        User user = new User();

        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());

        for(String name : newUser.getRoleNames()) {
            Role role = roleRepository.findRoleByName(name);
            if(role != null) ListRoles.add(role);
        }
        user.setUserRoles(ListRoles);
        return userRepository.save(user);
    }
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
