package tauru.springframework.WebApp.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Users")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "has_changed_address_infos")
    private Boolean hasChangedAddressInfos = Boolean.FALSE;

    @Column(name = "user_is_logged")
    private Boolean userIsLoggedIn = Boolean.FALSE;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Address> addressList;

    @OneToMany(mappedBy = "user")
    private List<AutomotiveRides> automotiveRidesList;

    @OneToMany(mappedBy = "user", cascade={CascadeType.ALL})
    private List<Roles> rolesList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    public List<AutomotiveRides> getAutomotiveRidesList() {
        return automotiveRidesList;
    }

    public void setAutomotiveRidesList(List<AutomotiveRides> automotiveRidesList) {
        this.automotiveRidesList = automotiveRidesList;
    }

    public Boolean getUserIsLoggedIn() {
        return userIsLoggedIn;
    }

    public void setUserIsLoggedIn(Boolean userIsLoggedIn) {
        this.userIsLoggedIn = userIsLoggedIn;
    }

    public Boolean getHasChangedAddressInfos() {
        return hasChangedAddressInfos;
    }

    public void setHasChangedAddressInfos(Boolean hasChangedAddressInfos) {
        this.hasChangedAddressInfos = hasChangedAddressInfos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User (String username, String password, String email) {

        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User() {

    }
}
