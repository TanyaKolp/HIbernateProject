package ru.apache_maven.models;

import org.springframework.stereotype.Component;

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
    private Integer id;
    @Column(name = "station_number")
    private Integer name;
    private Boolean shop;
    @Column()
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
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

    @Override
    public String toString() {
        return id.toString() + " - " + company.getName() + " - " + name + " - " ;
    }
}
