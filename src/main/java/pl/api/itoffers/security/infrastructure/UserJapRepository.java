package pl.api.itoffers.security.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.api.itoffers.security.domain.model.UserEntity;

interface UserJapRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);
}
