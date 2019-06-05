package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.Automotive;
import tauru.springframework.WebApp.entities.AutomotiveRides;

import java.util.List;

@Repository
public interface AutomotiveRidesRepository extends JpaRepository<AutomotiveRides, Long> {

}
