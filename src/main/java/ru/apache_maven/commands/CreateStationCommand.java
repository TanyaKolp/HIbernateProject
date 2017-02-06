package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;
import ru.apache_maven.models.Location;

import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tania on 12/21/16.
 */
@Component
@Named("station")
public class CreateStationCommand implements Creation {
    @Autowired
    SessionController sessionController;
    private GasStation gasStation;
    private Result result = new Result();
    private ArrayList<String> messages = new ArrayList<>();

    @Override
    public Result execute(List<String> args) {
        messages = new ArrayList<>();
        gasStation = new GasStation();
        try {
            sessionController.getSession().beginTransaction();
            if (setCompany(args.get(0)) | setStationNumber(args.get(1)) |
                    setShopAndCafe(args.get(2), args.get(3))) {
                sessionController.getSession().saveOrUpdate(gasStation);
                sessionController.getSession().getTransaction().commit();

                for (int i = 0; i < 4; i++) {
                    args.remove(0);
                }
                sessionController.getSession().beginTransaction();
                setLocation(args);
                sessionController.getSession().saveOrUpdate(gasStation);
                result.setSuccess(true);
                messages.add("Done");
            } else {
                result.setSuccess(false);
            }
            sessionController.getSession().getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            result.setSuccess(false);
            messages.add("sm is wrong");
        }
        result.setMessages(messages);
        return result;
    }

    private boolean setCompany(String companyName) {
        Criteria criteria = sessionController.getSession().createCriteria(Company.class);
        List<Company> companies = (List<Company>) criteria.list();
        for (Company c : companies) {
            if (c.getName().equalsIgnoreCase(companyName)) {
                gasStation.setCompany(c);
                return true;
            }
        }
        messages.add("ERROR!Company " + companyName + " doesn't exists.");
        return false;
    }

    private boolean setStationNumber(String stationNumber) {
        Integer number;
        try {
            number = new Integer(stationNumber);
            gasStation.setName(number);
            return true;
        } catch (NumberFormatException e) {
            messages.add("ERROR! Not a number for station number.");
            return false;
        }
    }

    private boolean setShopAndCafe(String shop, String cafe) {
        if (cafe.equalsIgnoreCase("y")) {
            gasStation.setCafe(true);
        } else if (cafe.equalsIgnoreCase("n")) {
            gasStation.setCafe(false);
        } else {
            messages.add("ERROR! Unknown value for cafe. Choose 'y' or 'n'.");
            return false;
        }
        if (shop.equalsIgnoreCase("y")) {
            gasStation.setCafe(true);
        } else if (shop.equalsIgnoreCase("n")) {
            gasStation.setCafe(false);
        } else {
            messages.add("ERROR! Unknown value for shop. Choose 'y' or 'n'.");
            return false;
        }
        return true;
    }

    private void setLocation(List<String> args) {
        Location location = new Location();
        if (!args.get(0).equalsIgnoreCase("-")) {
            location.setRegion(args.get(0));
        }
        if (!args.get(1).equalsIgnoreCase("-")) {
            location.setCity(args.get(1));
        }
        if (!args.get(2).equalsIgnoreCase("-")) {
            location.setRoadNumber(args.get(2));
        }
        String address = concatAddress(args);
        location.setAddress(address);
        location.setGasStations(gasStation);
        gasStation.setLocation(location);
        sessionController.getSession().saveOrUpdate(location);
    }

    private String concatAddress(List<String> args) {
        String address = "";
        for (int i = 0; i < args.size() - 1; i++) {
            address += args.get(i) + ", ";
        }
        address += args.get(args.size() - 1);
        return address;
    }
}