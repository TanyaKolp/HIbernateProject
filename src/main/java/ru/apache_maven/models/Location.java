package ru.apache_maven.models;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;
import ru.apache_maven.models.GasStation;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tania on 11/23/16.
 */
@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @Column(name = "location_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer location_id;

    private String region;
    private String city;
    private String roadNumber;
    private String address;

    @OneToOne(mappedBy = "location", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private GasStation gasStation;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public GasStation getGasStations() {
        return gasStation;
    }

    public void setGasStations(GasStation gasStations) {
        this.gasStation = gasStations;
    }
}
