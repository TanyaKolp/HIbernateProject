package ru.apache_maven.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by tania on 11/15/16.
 */
@Entity
@Table(name = "Companies")
public class Company {
    public Company() {
    }

    public Company(String company_name) {
        this.name = company_name;
    }

    @Id
    @Column(name = "company_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "companies", fetch = FetchType.EAGER)
    private Set<User> users;

    @OneToMany(mappedBy = "company")
    private List<GasStation> stations;

    @OneToMany(mappedBy = "company")
    private List<FuelType> fuelTypes;

    @Column(name = "company_name", length = 20)
    private String name;


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

    public List<GasStation> getStations() {
        return stations;
    }

    public void setStations(List<GasStation> stations) {
        this.stations = stations;
    }

    public List<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    public void setFuelTypes(List<FuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return id.toString() + " - " + name;
    }
}
