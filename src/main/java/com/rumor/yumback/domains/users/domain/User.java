package com.rumor.yumback.domains.users.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String name;

    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String name, String email, String picture, UserRole role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
