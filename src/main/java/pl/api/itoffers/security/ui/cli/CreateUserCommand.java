package pl.api.itoffers.security.ui.cli;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateUserCommand {

    @ShellMethod(value="Create user that can authenticate")
    public String create() {
        return "User created";
    }
}
