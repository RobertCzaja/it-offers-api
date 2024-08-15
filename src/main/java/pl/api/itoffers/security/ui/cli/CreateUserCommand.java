package pl.api.itoffers.security.ui.cli;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.domain.model.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static pl.api.itoffers.security.domain.model.UserRole.ROLE_USER;

@Transactional
@ShellComponent
public class CreateUserCommand {

    private final EntityManager entityManager;

    public CreateUserCommand(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @ShellMethod(key="create-user", value="Create user that can authenticate")
    public String create(
            String email,
            String password
    ) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        user.setDate(LocalDateTime.now());
        user.setRoles(new String[]{UserRole.ROLE_ADMIN.toString()});

        entityManager.persist(user);
        entityManager.flush();

        return String.format("User %s created (%s)", email, password);
    }
}
