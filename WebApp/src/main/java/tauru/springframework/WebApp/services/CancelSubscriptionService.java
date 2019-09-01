package tauru.springframework.WebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.CancelSubscription;
import tauru.springframework.WebApp.repositories.CancelSubscriptionRepository;

import java.util.logging.Logger;

@Service
public class CancelSubscriptionService {

    @Autowired
    private CancelSubscriptionRepository cancelSubscriptionRepository;

    private static final Logger LOGGER = Logger.getLogger(CancelSubscriptionService.class.getName());

    public void saveCancelSubscription (CancelSubscription cancelSubscription) {

        cancelSubscriptionRepository.save(cancelSubscription);
    }

    public void deleteSubscription(CancelSubscription subscription) {

        cancelSubscriptionRepository.delete(subscription);
    }

    public CancelSubscription findCanceSubscriptionById (Long cancelSubscriptionId) {

        CancelSubscription subscription = cancelSubscriptionRepository.findCancelSubscriptionById(cancelSubscriptionId);

        if (subscription != null) {
            return  subscription;
        } else {
            LOGGER.info("Couldn't find subscription with id " + cancelSubscriptionId + " . Returning null !");
            return null;
        }
    }
}
