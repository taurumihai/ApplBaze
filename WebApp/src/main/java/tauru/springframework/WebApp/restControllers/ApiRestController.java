package tauru.springframework.WebApp.restControllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestController.class);

    private UserService userService;

    @Autowired
    public ApiRestController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping("/users")
    public List<User> getAllUsers(){

        return userService.findAllUsers();
    }

    @RequestMapping(value = "/users/{id}")
    public User getSpecificUserById(@PathVariable(name = "id") String userId) {

        User specifiedUser = userService.findUserById(Long.valueOf(userId));

        if (specifiedUser != null) {

            LOGGER.info("Returning user found in DB!! " + specifiedUser.objectMapper(specifiedUser));
            return specifiedUser;
        } else {

            LOGGER.error("User not found !!!");
            return null;
        }

    }
}
