package tauru.springframework.WebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.Automotive;
import tauru.springframework.WebApp.entities.AutomotiveRides;
import tauru.springframework.WebApp.repositories.AutomotiveRidesRepository;

import java.util.List;

@Service
public class AutomotiveRidesService {

    @Autowired
    private AutomotiveRidesRepository automotiveRidesRepository;


    public void saveAutomotiveRides(AutomotiveRides automotiveRides) {

        automotiveRidesRepository.save(automotiveRides);
    }

    public List<AutomotiveRides> findAll()
    {
        return automotiveRidesRepository.findAll();
    }

    public AutomotiveRides findAutomotiveRideById(Long automotiveRideId)
    {
       for (AutomotiveRides rides : automotiveRidesRepository.findAll())
       {
           if (rides.getId().equals(automotiveRideId))
           {
               return rides;
           }
       }

       return null;
    }

}
