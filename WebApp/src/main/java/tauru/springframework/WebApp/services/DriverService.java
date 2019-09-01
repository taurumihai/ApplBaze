package tauru.springframework.WebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.Driver;
import tauru.springframework.WebApp.repositories.DriverRepository;

import java.util.logging.Logger;

@Service
public class DriverService {


    @Autowired
    private DriverRepository driverRepository;

    private static final Logger LOGGER = Logger.getLogger(DriverService.class.getName());

    public void saveDriver(Driver driver) {

        LOGGER.info("Trying to delete driver " + driver);

        if (driver != null) {

            driverRepository.save(driver);
        }
    }

    public void deleteDriverById (Long driverId) {

        if (driverId != null) {
            LOGGER.info("Deleteing driver ..." + driverId);
            driverRepository.deleteById(driverId);
            LOGGER.info("Driver deleted ..."+ driverId);
        }
    }

}
