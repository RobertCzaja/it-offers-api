package pl.api.itoffers.security.infrastructure;

import org.springframework.stereotype.Repository;
import pl.api.itoffers.security.application.User;

import java.util.ArrayList;
import java.util.Objects;

@Repository
public class UserInMemoryRepository {

    private ArrayList<User> users;

    public UserInMemoryRepository() {
        this.users = new ArrayList<>();
        this.users.add(new User("a@a.pl","123456", "FirstName", "LastName"));
    }

    public User findUserByEmail(String email){

        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        throw new RuntimeException("User with email "+email+" not exists");
    }
}
