package tauru.springframework.WebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.Address;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.repositories.AddressRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    public void saveAddress(Address address) {

        addressRepository.save(address);
    }

    public List<Address> findAllAddresses(String streetName) {

        return addressRepository.findAllAddressesByStreetName(streetName);
    }


}
