package com.younes.reskilingproject.bookManagement.security.repository;

import com.younes.reskilingproject.bookManagement.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    public <Optional>Role findRoleByName(String name);
}
