package cd.bensmile.hoaxify.controllers;

import cd.bensmile.hoaxify.models.User;
import cd.bensmile.hoaxify.services.UserService;
import cd.bensmile.hoaxify.shared.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    GenericResponse createUser(@RequestBody User user) {
        userService.save(user);
        return new GenericResponse("User saved");
    }

}
