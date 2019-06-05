package tauru.springframework.WebApp.entities;

import javax.persistence.*;

@Entity
@Table(name = "User_Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String county;

    private String street;

    private String zipCode;

    private String streetNumber;

    private Boolean hasCompletedAddressesInfo = Boolean.FALSE;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Boolean getHasCompletedAddressesInfo() {
        return hasCompletedAddressesInfo;
    }

    public void setHasCompletedAddressesInfo(Boolean hasCompletedAddressesInfo) {
        this.hasCompletedAddressesInfo = hasCompletedAddressesInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address(){

    }
}
