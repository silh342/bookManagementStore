package com.younes.reskilingproject.bookManagement.security.model;

import java.util.Set;

public class UserRequestBody {

    private String username;
    private String password;
    private Set<String> roleNames;

    public UserRequestBody() {
    }
    public UserRequestBody(String username, String password, Set<String> roleNames) {
        this.username = username;
        this.password = password;
        this.roleNames = roleNames;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<String> getRoleNames() {
        return roleNames;
    }
    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }
}
