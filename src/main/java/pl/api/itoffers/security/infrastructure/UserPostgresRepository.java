package pl.api.itoffers.security.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.User;
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
        return userEntity.castToUser();
    }

    @Override
    public void save(UserEntity user) {
        entityManager.persist(user);
        entityManager.flush();
    }
}
