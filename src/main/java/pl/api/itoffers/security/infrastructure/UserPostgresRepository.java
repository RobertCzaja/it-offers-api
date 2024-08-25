package pl.api.itoffers.security.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.domain.exception.CouldNotCreateUser;
import pl.api.itoffers.security.domain.exception.UserNotFound;
import pl.api.itoffers.security.domain.model.UserEntity;

@Transactional
@Repository
public class UserPostgresRepository implements UserRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserJapRepository userJapRepository;

    @Override
    public User findUserByEmail(String email) {
        UserEntity userEntity = userJapRepository.findByEmail(email);

        if (null == userEntity ) {
            throw UserNotFound.with(email);
        }

        return userEntity.castToUser();
    }

    @Override
    public void save(UserEntity user) throws CouldNotCreateUser {
        try {
            entityManager.persist(user);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw CouldNotCreateUser.becauseEmailIsAlreadyRegistered(user.getEmail());
        }
    }

    @Override
    public void deleteAll() {
        userJapRepository.deleteAll();
    }
}
