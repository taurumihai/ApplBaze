package tauru.springframework.WebApp.entities;

import javax.persistence.*;

@Entity
@Table(name = "Cancel_Subscription")
public class CancelSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CancelSubscription(String message) {
        this.message = message;
    }
    public CancelSubscription() {

    }

    @Override
    public String toString() {

        return this.message;
    }
}
