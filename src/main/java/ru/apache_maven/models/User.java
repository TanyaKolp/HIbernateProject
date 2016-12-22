package ru.apache_maven.models;

import ru.apache_maven.models.Company;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tania on 11/14/16.
 */
@Entity
@Table(name = "Users")
public class User {
    public User(){}
    public User(String login, String password) {
        this.login =login;
        this.password =password;
    }


    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @ManyToMany
    @JoinTable(name = "FavoriteCompanies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> companies;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(length = 20, unique = true, nullable = false)
    private String login;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean admin = false;

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Set<Company> getCompanies() {
        if(companies==null) {
            companies = new HashSet<>();
        }
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public String toString() {
        return getUser_id().toString() + "-" ;
        //+ "-" + getCompany().getCompany_name();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginAndPassword(String login,String password){
        this.login = login;
        this.password = password;
    }
}
