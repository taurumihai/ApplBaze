package tauru.springframework.WebApp.entities;


import tauru.springframework.WebApp.enums.AutomotiveTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Automotives")
public class Automotive  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "brand")
    private String brand;

    @Column(name = "automotive_type")
    private AutomotiveTypeEnum automotiveType;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "horse_power")
    private Integer horsePower;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Rides", joinColumns ={@JoinColumn(name = "automotiveId")}, inverseJoinColumns = {@JoinColumn(name = "automotiveRidesId")})
    private List<AutomotiveRides> automotiveRidesList;

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public AutomotiveTypeEnum getAutomotiveType() {
        return automotiveType;
    }

    public void setAutomotiveType(AutomotiveTypeEnum automotiveType) {
        this.automotiveType = automotiveType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Automotive() {

    }

    public List<AutomotiveRides> getAutomotiveRidesList() {
        return automotiveRidesList;
    }

    public void setAutomotiveRidesList(List<AutomotiveRides> automotiveRidesList) {
        this.automotiveRidesList = automotiveRidesList;
    }

    @Override
    public String toString() {

        return "Marca " + this.brand + this.automotiveType + "Culoare " + this.color + "Capacitate " + this.capacity + "Cai putere " + this.horsePower + "Descriere " + this.description;
    }
}
