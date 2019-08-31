package tauru.springframework.WebApp.entities;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tauru.springframework.WebApp.repositories.UserRepository;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class UserTest {

    private static final String userName = "DummyUser";
    private static final String password = "password";
    private static final String email = "test@yahoo.com";
    private static final Logger LOGGER = Logger.getLogger(UserTest.class.getName());
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initUserTest() {
        LOGGER.info("Before test");
        LOGGER.info("Initializing user for tests !");
        user = new User();
    }

    @Test
    public void testUserConstructor() {

        LOGGER.info("Creating user by constructor with 3 params");
        user = new User(userName, password, email);
        userRepository.save(user);
        LOGGER.info("Saving user creating by constructor");

        assertEquals(user.getUsername(), userName);
        assertEquals(user.getPassword(), password);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void testSaveUser() {
        LOGGER.info("Creating Dummy User Save Method");;
        user.setUsername(userName);
        userRepository.save(user);

        User findSavedUser = userRepository.findDriverByUserName(userName);
        LOGGER.info("Save method userName should be equals");
        assertEquals(findSavedUser.getUsername(), userName);
    }

    @Test
    public void testDeleteUser() {
        LOGGER.info("Creating Dummy User Delete Method");
        user.setUsername(userName);
        userRepository.save(user);
        userRepository.delete(user);
        User checkingIfUserExist = userRepository.findDriverByUserName(userName);
        LOGGER.info( "Checking if user was deleted after calling deleteUser method from repo " + (checkingIfUserExist == null));
        assertNull("Should be null", checkingIfUserExist);
    }

    @After
    public void deleteUserAfterTests() {
        LOGGER.info("Deleting user after tests are done " + user);
        userRepository.delete(user);
    }
}
