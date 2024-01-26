package com.younes.reskillingproject.userManagement.entity.security.repository;

import com.younes.reskillingproject.userManagement.entity.security.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
