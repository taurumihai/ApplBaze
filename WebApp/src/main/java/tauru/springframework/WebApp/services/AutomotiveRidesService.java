package tauru.springframework.WebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.AutomotiveRides;
import tauru.springframework.WebApp.repositories.AutomotiveRidesRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class AutomotiveRidesService {

    @Autowired
    private AutomotiveRidesRepository automotiveRidesRepository;

    private static final Logger LOGGER = Logger.getLogger(AutomotiveRidesService.class.getName());


    public void saveAutomotiveRides(AutomotiveRides automotiveRides) {

        automotiveRidesRepository.saveAndFlush(automotiveRides);
    }

    public List<AutomotiveRides> findAll()
    {
        return automotiveRidesRepository.findAll();
    }

    public AutomotiveRides findAutomotiveRideById(Long automotiveRideId)
    {
        List<AutomotiveRides> ridesList = new ArrayList<AutomotiveRides>();
       for (AutomotiveRides rides : automotiveRidesRepository.findAll())
       {
           if (rides.getId().equals(automotiveRideId))
           {
               ridesList.add(rides);

           }
       }

       if (ridesList.isEmpty()) {

           return null;
       } else {

           return ridesList.get(0);
       }
    }

    public List<AutomotiveRides> findAllAutomotiveRidesByUserId(Long userId) {

        List<AutomotiveRides> returnedList = new ArrayList<AutomotiveRides>();

        for (AutomotiveRides ride : findAll()) {

            if (ride.getUser() != null && ride.getUser().getId().equals(userId)) {
                returnedList.add(ride);
            } else {
                continue;
            }
        }

        if (returnedList.isEmpty()) {
            LOGGER.info("Can't find rides with user id " + userId);
            return null;
        } else {

            return returnedList;
        }
    }
}
