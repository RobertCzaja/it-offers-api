package pl.api.itoffers.security.ui.cli;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.security.application.service.UserService;
import pl.api.itoffers.security.domain.model.UserRole;



@Transactional
@ShellComponent
public class CreateUserCommand {

    @Autowired
    private UserService userService;

    @ShellMethod(key="create-user", value="Create an admin User")
    public String create(
            String email,
            String password
    ) {
        Long userId = userService.create(new String[]{UserRole.ROLE_ADMIN.toString()}, email, password);
        return String.format("User %s created (%s) with ID: %d", email, password, userId);
    }
}
