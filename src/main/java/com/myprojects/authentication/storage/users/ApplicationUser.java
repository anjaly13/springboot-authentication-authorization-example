package com.myprojects.authentication.storage.users;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_users")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "user_password")
    private String password;

    @Column(name = "role")
    private String role;
}
