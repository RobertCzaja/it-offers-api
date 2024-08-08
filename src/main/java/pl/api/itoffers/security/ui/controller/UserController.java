package pl.api.itoffers.security.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public final static String PATH = "/user";

    public UserController() {
    }

    /** TODO Return type change for specific Response object */
    @PostMapping(UserController.PATH)
    public String create()
    {
        return "User Created";
    }
}
