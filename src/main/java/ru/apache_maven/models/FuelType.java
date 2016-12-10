package ru.apache_maven.models;

import javax.persistence.*;

/**
 * Created by tania on 11/23/16.
 */
@Entity
@Table(name = "FuelType")
public class FuelType {
    @Id
    @Column(name = "station_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fuelType_id;

    private String title;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Integer getFuelType_id() {
        return fuelType_id;
    }

    public void setFuelType_id(Integer fuelType_id) {
        this.fuelType_id = fuelType_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
