package pl.api.itoffers.security.infrastructure;

import org.springframework.stereotype.Repository;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.model.UserEntity;

import java.util.ArrayList;
import java.util.Objects;

@Deprecated
@Repository
public class UserInMemoryRepository implements UserRepository {

    private ArrayList<User> users;

    public UserInMemoryRepository() {
        this.users = new ArrayList<>();
        this.users.add(new User("a@a.pl","admin", "John", "Doe"));
    }

    public User findUserByEmail(String email){

        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        throw new RuntimeException("User with email "+email+" not exists");
    }

    @Override
    public void save(UserEntity user) {
        throw new RuntimeException("Not implemented yet");
    }
}
