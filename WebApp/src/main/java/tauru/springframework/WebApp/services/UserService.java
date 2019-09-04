package tauru.springframework.WebApp.services;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.Driver;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {

        List<User> userList = userRepository.findAll();
        return userList;

    }

    public void saveUSer(User user) {

        if (user != null ) {

            userRepository.save(user);
        }
    }

    public User findUserByUserName(String username) {

        List<User> userList = userRepository.findAll();

        if (userList != null) {

            for(User user : userList) {

                if (user.getUsername().equals(username)) {

                    return user;
                }
            }
        }

        System.out.println("User not found");
        return null;
    }

    public User findUSerByUSernameAndPassword(String userName, String password) {

        User user = userRepository.findUserByUserNameAndPassword(userName, password);

        User entity = user;
        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = (User) ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    public Driver findDriverRegisteredByUserName(String username) {

        User findUser = userRepository.findDriverByUserName(username);

        if (findUser != null && findUser.getDriver() != null) {

            return findUser.getDriver();
        }

        return null;
    }

    public User unproxy(User proxied)
    {
        User entity = proxied;
        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = (User) ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

//    public User findUserById(Long userId) {
//
//        return userRepository.findUserById(userId);
//    }

}
