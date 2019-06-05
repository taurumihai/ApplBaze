package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "select address from Address address where address.street = ?1")
    List<Address> findAllAddressesByStreetName(String streetName);


}
