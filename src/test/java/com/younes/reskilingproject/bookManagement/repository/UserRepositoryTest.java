package com.younes.reskilingproject.bookManagement.repository;


import com.younes.reskillingproject.userManagement.security.entity.Role;
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.repository.RoleRepository;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void UserRepository_findAllUsers_ReturnUsers() {
        // Arrange necessary data to create a user
        Role user = new Role("ROLE_USER");
        User younes = new User("younes", "28031996", "younesesebbar089@gmail.com", new HashSet<>());
        younes.getUserRoles().add(user);

        //  Act
        userRepository.save(younes);
        List<User> listUsers = userRepository.findAll();

        // Assert
        Assertions.assertNotNull(listUsers);
        Assertions.assertEquals(listUsers.size(), 1);
    }

    @Test
    public void UserRepository_findByUsername_ReturnUser() {
        Role user = new Role("ROLE_USER");
        User younes = new User("younes", "28031996", "younesesebbar089@gmail.com", new HashSet<>());
        younes.getUserRoles().add(user);

        userRepository.save(younes);
        Optional<User> findUser = userRepository.findByUsername(younes.getUsername());

        Assertions.assertNotNull(findUser);
    }
}
