package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.Automotive;

@Repository
public interface AutomotiveRepository extends JpaRepository<Automotive, Long> {

    @Query("select auto from Automotive auto where auto.id = ?1")
    Automotive findAutomotiveById(Long automotiveId);
}
