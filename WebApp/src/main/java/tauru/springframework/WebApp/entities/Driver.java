package tauru.springframework.WebApp.entities;

import javax.persistence.*;

@Entity
@Table(name = "Drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "driver_is_registered")
    private Boolean isRegistered = Boolean.FALSE;

    @OneToOne(mappedBy = "driver")
    private User user;

    @OneToOne(mappedBy = "driver")
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private CancelSubscription cancelSubscription;


    public Long getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public Driver() {

    }

    public Driver(Integer age, Integer experienceYears) {

        this.age = age;
        this.experienceYears = experienceYears;
    }

    public CancelSubscription getCancelSubscription() {
        return cancelSubscription;
    }

    public void setCancelSubscription(CancelSubscription cancelSubscription) {
        this.cancelSubscription = cancelSubscription;
    }
}
