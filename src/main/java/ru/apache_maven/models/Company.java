package ru.apache_maven.models;

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
        this.company_name = company_name;
    }

    @Id
    @Column(name = "company_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer company_id;

    @ManyToMany(mappedBy = "companies", fetch = FetchType.EAGER)
    private Set<User> users;

    @OneToMany(mappedBy = "company")
    private List<GasStation> stations;

    @OneToMany(mappedBy = "company")
    private List<FuelType> fuelTypes;

    @Column(length = 20)
    private String company_name;

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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
}
