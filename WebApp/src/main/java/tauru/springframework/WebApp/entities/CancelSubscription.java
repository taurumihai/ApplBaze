package tauru.springframework.WebApp.entities;

import javax.persistence.*;

@Entity
@Table(name = "Cancel_Subscription")
public class CancelSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
