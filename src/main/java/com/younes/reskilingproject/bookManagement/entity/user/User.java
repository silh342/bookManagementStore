package com.younes.reskilingproject.bookManagement.entity.user;

import jakarta.persistence.*;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column
    private String username;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name= "role_id")
    private Role role;

    // Constructor
    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter Setter
    public long getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // ToString method
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
