package pl.api.itoffers.security.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.api.itoffers.security.domain.model.UserEntity;

import java.util.List;

/**
 * TODO only for learning purpose, to refactor
 */
public interface UserPostgresqlRepository extends JpaRepository<UserEntity, Long>
{
    @Query("SELECT u FROM UserEntity u WHERE (u.email = :email)")
    List<UserEntity> listUser(@Param("email") String email);
}
