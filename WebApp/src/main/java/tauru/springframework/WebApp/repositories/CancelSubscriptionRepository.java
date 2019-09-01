package tauru.springframework.WebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tauru.springframework.WebApp.entities.CancelSubscription;

@Repository
public interface CancelSubscriptionRepository extends JpaRepository<CancelSubscription, Long> {

    @Query(value = "select subscription from CancelSubscription subscription where subscription.id = ?1")
    CancelSubscription findCancelSubscriptionById(Long subscriptionId);
}
