package com.younes.reskilingproject.bookManagement.entity.user;


import jakarta.persistence.*;

@Entity(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(name = "role_name")
    private String roleName;

    // Constructor
    public Role() {};
    public Role(String name) {
        roleName = name;
    }

    // Getter Setter
    public long getRoleId() {
        return roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // ToString method

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
