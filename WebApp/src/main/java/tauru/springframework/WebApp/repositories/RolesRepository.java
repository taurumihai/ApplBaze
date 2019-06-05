package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {


    @Query(nativeQuery = true, value = "select id from Roles rol where rol.id = ?1")
    Roles findRoleById (Long roleId);
}
