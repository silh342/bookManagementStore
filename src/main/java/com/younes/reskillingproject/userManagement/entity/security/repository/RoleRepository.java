package com.younes.reskillingproject.userManagement.entity.security.repository;

import com.younes.reskillingproject.userManagement.entity.security.model.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findRoleByName(String name);
}
