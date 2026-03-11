package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(unique=true)
    private String email;

    @NotEmpty
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phone;

    public enum Role {
        CUSTOMER,
        ADMIN,
        DRIVER
    }

    public User(String name, String email, String passwordHash, Role role, String phone) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.phone = phone;
    }
}
