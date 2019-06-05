package tauru.springframework.WebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.Driver;
import tauru.springframework.WebApp.repositories.DriverRepository;

@Service
public class DriverService {


    @Autowired
    private DriverRepository driverRepository;

    public void saveDriver(Driver driver) {

        if (driver != null) {

            driverRepository.save(driver);
        }
    }

}
