package com.younes.reskilingproject.bookManagement.repository.user;

import com.younes.reskilingproject.bookManagement.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
