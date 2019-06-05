package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
