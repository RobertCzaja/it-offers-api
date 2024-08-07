package pl.api.itoffers.security.ui.cli;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.security.domain.model.UserEntity;

import java.time.LocalDate;

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
        user.setDate(LocalDate.now());

        entityManager.persist(user);
        entityManager.flush();

        return String.format("User %s created (%s)", email, password);
    }
}