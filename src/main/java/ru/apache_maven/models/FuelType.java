package ru.apache_maven.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by tania on 11/23/16.
 */
@Entity
@Table(name = "FuelType")
public class FuelType {
    @Id
    @Column(name = "fuelType_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String name;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return id.toString() + " - " + company.getName() + " - " + name;
    }
}
