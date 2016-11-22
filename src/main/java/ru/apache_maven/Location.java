package ru.apache_maven;

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

    @OneToMany(mappedBy = "location")
    private List<GasStation> gasStations;

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

    public List<GasStation> getGasStations() {
        return gasStations;
    }

    public void setGasStations(List<GasStation> gasStations) {
        this.gasStations = gasStations;
    }
}
