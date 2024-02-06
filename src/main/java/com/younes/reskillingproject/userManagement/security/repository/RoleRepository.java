package com.younes.reskillingproject.userManagement.security.repository;

import com.younes.reskillingproject.userManagement.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    public <Optional>Role findRoleByName(String name);
}
