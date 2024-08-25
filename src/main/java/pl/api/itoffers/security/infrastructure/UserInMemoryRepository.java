package pl.api.itoffers.security.infrastructure;

import org.springframework.stereotype.Repository;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.exception.UserNotFound;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.domain.model.UserRole;

import java.util.ArrayList;
import java.util.Objects;

@Repository
public class UserInMemoryRepository implements UserRepository {

    public static final String EMAIL_ADMIN = "admin@admin.pl";
    public static final String PASSWORD_ADMIN = "admin1234";
    public static final String EMAIL_USER = "a@a.pl";
    public static final String PASSWORD_USER = "admin1234";

    private ArrayList<User> users;

    public UserInMemoryRepository() {
        this.users = new ArrayList<>();
        this.users.add(
            new User(
                EMAIL_USER,
                "$2a$10$/ABBpyVoo3zbZ7Sx1ZXD9OhHD8vVhwJGpcRAUu9kl8k2DbQrCLoq6",
                "John",
                "Doe",
                new UserRole[]{UserRole.ROLE_USER}
            )
        );
        this.users.add(
            new User(
                EMAIL_ADMIN,
                "$2a$10$/ABBpyVoo3zbZ7Sx1ZXD9OhHD8vVhwJGpcRAUu9kl8k2DbQrCLoq6",
                "Admin",
                "Smith",
                new UserRole[]{UserRole.ROLE_ADMIN}
            )
        );
    }

    public User findUserByEmail(String email){

        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        throw UserNotFound.with(email);
    }

    @Override
    public void save(UserEntity user) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void deleteAll() { throw new RuntimeException("Not implemented yet"); }
}
