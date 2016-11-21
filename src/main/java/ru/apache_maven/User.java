package ru.apache_maven;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tania on 11/14/16.
 */
@Entity
@Table(name = "Users")
public class User {
    public User() {
    }

    public User(String name) {
        this.name = name;
    }


    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    //    @ManyToMany
//    @JoinTable(name = "user_company",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "company_id"))
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "user_name")
    private String name;


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String toString() {
        return getUser_id().toString() + "-" + getName();
        //+ "-" + getCompany().getCompany_name();

    }
}
