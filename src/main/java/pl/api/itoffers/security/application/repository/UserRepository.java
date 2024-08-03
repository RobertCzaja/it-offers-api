package pl.api.itoffers.security.application.repository;

import pl.api.itoffers.security.domain.User;

public interface UserRepository {
    User findUserByEmail(String email);
}
