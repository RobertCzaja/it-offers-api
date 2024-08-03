package pl.api.itoffers.security.application.repository;

import pl.api.itoffers.security.application.User;

public interface UserRepository {
    User findUserByEmail(String email);
}
