package pl.api.itoffers.security.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.api.itoffers.security.domain.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "\"user\"") // TODO to find out why Hibernate can't generate "" by itself
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique=true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String[] roles;

    @Deprecated
    public User castToUser()
    {
        return new User(email, password, "", "", new UserRole[]{}/*todo*/);
    }
}
