package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select users from User users where users.username = ?1 and users.password = ?2")
    List<User> findUserByUserNameAndPassword(String userName, String password);

    @Query("select users from User users where users.username = ?1")
    User findDriverByUserName(String username);
}
