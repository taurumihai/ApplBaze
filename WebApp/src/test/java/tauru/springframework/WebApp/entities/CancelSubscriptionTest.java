package tauru.springframework.WebApp.entities;


import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tauru.springframework.WebApp.repositories.CancelSubscriptionRepository;
import tauru.springframework.WebApp.repositories.UserRepository;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class CancelSubscriptionTest {

    private static final Logger LOGGER = Logger.getLogger(CancelSubscriptionTest.class.getName());
    private static final String message = "Mesaj Static Final";
    private static final String testMessage = "Mesaj Test";
    private CancelSubscription cancelSubscription;

    @Autowired
    private CancelSubscriptionRepository cancelSubscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initTests() {

        LOGGER.info("Start initialize Cancel subscription tests");

        cancelSubscription = new CancelSubscription();
    }

    @Test
    public void testConstructorCancelSubs() {

        LOGGER.info("Creating new object cancel subs");
        CancelSubscription testObject = new CancelSubscription(message);

        assertEquals(testObject.getMessage(), message);
    }

    @Test
    public void testDeleteSaveSubs() {

        LOGGER.info("start testing delete/saving subscription into DB");
        User dummyUser = new User();
        dummyUser.setLastName("dummy");

        userRepository.save(dummyUser);

        cancelSubscription = new CancelSubscription(message);
        cancelSubscription.setUser(dummyUser);
        cancelSubscriptionRepository.save(cancelSubscription);
        //coment pt ambele metode
//        cancelSubscriptionRepository.delete(cancelSubscription);
    }
}
