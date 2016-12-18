package ru.apache_maven.models;

import javax.persistence.*;

/**
 * Created by tania on 11/21/16.
 */
@Entity
@Table(name = "GasStations")
public class GasStation {
    @Id
    @Column(name = "station_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stationId;
    @Column(name = "station_number")
    private Integer stationNumber;
    private Boolean shop;
    private Boolean cafe;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Boolean getShop() {
        return shop;
    }

    public void setShop(Boolean shop) {
        this.shop = shop;
    }

    public Boolean getCafe() {
        return cafe;
    }

    public void setCafe(Boolean cafe) {
        this.cafe = cafe;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
