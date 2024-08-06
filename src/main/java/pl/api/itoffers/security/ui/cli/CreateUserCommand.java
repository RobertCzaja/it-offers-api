package pl.api.itoffers.security.ui.cli;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateUserCommand {

    public CreateUserCommand() {
    }

    @ShellMethod(key="create-user", value="Create user that can authenticate")
    public String create(
            String email,
            String password
    ) {
        return String.format("User %s created (%s)", email, password);
    }
}
