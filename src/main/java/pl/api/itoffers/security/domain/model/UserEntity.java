package pl.api.itoffers.security.domain.model;

import jakarta.persistence.*;
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

    private String password;

    private LocalDateTime date;

    // TODO add roles

    @Deprecated
    public User castToUser()
    {
        return new User(email, password, "", "", new UserRole[]{UserRole.ROLE_USER} /*todo pass roles from Entity state*/);
    }
}
