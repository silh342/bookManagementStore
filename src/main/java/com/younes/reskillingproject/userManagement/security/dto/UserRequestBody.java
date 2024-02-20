package com.younes.reskillingproject.userManagement.security.dto;

import java.util.Set;

public class UserRequestBody {

    private String username;
    private String password;
    private String email;
    private Set<String> roleNames;

    public UserRequestBody() {
    }
    public UserRequestBody(String username, String password,String email ,Set<String> roleNames) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleNames = roleNames;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
