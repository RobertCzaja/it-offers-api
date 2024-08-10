package pl.api.itoffers.security.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "\"user\"") // TODO to find out why Hibernate can't generate "" by itself
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String email;

    private String password;

    private LocalDateTime date;
}
