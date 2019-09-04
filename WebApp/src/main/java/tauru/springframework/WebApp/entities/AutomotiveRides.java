package tauru.springframework.WebApp.entities;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "automotive_rides")
public class AutomotiveRides {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name =  "duration")
    private Integer duration; // in minutes

    @Column(name = "ride_is_taken")
    private Boolean rideIsTaken = Boolean.FALSE;

    @Column(name = "amount_of_value_user_is_payed")
    private Double amountOfValueUserIsPayed;

    @Column(name = "ride_start_at")
    private Date rideStartAt;

    @Column(name ="ride_is_completed")
    private Boolean rideIsCompleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id")
    private User user;

    @ManyToMany(mappedBy = "automotiveRidesList")
    private List<Automotive> automotiveList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getRideStartAt() {
        return rideStartAt;
    }

    public void setRideStartAt(Date rideStartAt) {
        this.rideStartAt = rideStartAt;
    }

    public Double getAmountOfValueUserIsPayed() {
        return amountOfValueUserIsPayed;
    }

    public Boolean getRideIsTaken() {
        return rideIsTaken;
    }

    public void setRideIsTaken(Boolean rideIsTaken) {
        this.rideIsTaken = rideIsTaken;
    }

    public void setAmountOfValueUserIsPayed(Double amountOfValueUserIsPayed) {
        this.amountOfValueUserIsPayed = amountOfValueUserIsPayed;
    }

    public List<Automotive> getAutomotiveList() {
        return automotiveList;
    }

    public void setAutomotiveList(List<Automotive> automotiveList) {
        this.automotiveList = automotiveList;
    }

    public AutomotiveRides() {

    }

    public Boolean getRideIsCompleted() {
        return rideIsCompleted;
    }

    public void setRideIsCompleted(Boolean rideIsCompleted) {
        this.rideIsCompleted = rideIsCompleted;
    }
}
