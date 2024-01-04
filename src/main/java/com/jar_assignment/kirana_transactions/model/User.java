package com.jar_assignment.kirana_transactions.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue
    private int id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLoginAt;
    @Column(nullable = false)
    private RoleTypes role;

    public enum RoleTypes {
        ROLE_USER, ROLE_ADMIN
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
    public String getRole() {
        return role.name();
    }
    public void setRole(RoleTypes role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }


}
