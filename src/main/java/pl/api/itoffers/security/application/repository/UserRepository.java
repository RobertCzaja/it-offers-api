package pl.api.itoffers.security.application.repository;

import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.domain.exception.CouldNotCreateUser;
import pl.api.itoffers.security.domain.model.UserEntity;

public interface UserRepository {
    User findUserByEmail(String email);

    void save(UserEntity user) throws CouldNotCreateUser;
}
