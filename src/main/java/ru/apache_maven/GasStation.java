package ru.apache_maven;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tania on 11/21/16.
 */
@Entity
@Table(name = "GasStations")
public class GasStation {
    @Id
    @Column(name = "station_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer station_id;

    private Integer station_number;
    private Boolean shop;
    private Boolean cafe;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Integer getStation_id() {
        return station_id;
    }

    public void setStation_id(Integer station_id) {
        this.station_id = station_id;
    }

    public Integer getStation_number() {
        return station_number;
    }

    public void setStation_number(Integer station_number) {
        this.station_number = station_number;
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
