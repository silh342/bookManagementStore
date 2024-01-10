package com.younes.reskilingproject.bookManagement.repository.user;

import com.younes.reskilingproject.bookManagement.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
