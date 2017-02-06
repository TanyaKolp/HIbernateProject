package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;
import ru.apache_maven.models.GasStation;

import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tania on 12/22/16.
 */
@Component
@Named("fuelType")
public class CreateFuelTypeCommand implements Creation {
    @Autowired
    SessionController sessionController;
    private FuelType fuelType;
    private Result result = new Result();
    private ArrayList<String> messages = new ArrayList<>();
    @Override
    public Result execute(List<String> args) {
        try {
            sessionController.getSession().beginTransaction();
            fuelType = new FuelType();
            if(!setCompany(args.get(0)) || !setPrice(args.get(2))){
                result.setSuccess(false);
            }
            fuelType.setName(args.get(1));
            sessionController.getSession().saveOrUpdate(fuelType);
            sessionController.getSession().getTransaction().commit();
            result.setSuccess(true);
            messages.add("Done.");
        } catch (HibernateException e) {
            sessionController.getSession().getTransaction().rollback();
            result.setSuccess(false);
            messages.add("sn is wrong");
            e.printStackTrace();
        }
        result.setMessages(messages);
        return result;
    }

    private boolean setPrice(String price) {
        Float priceNumber;
        try {
            priceNumber = new Float(price);
            fuelType.setPrice(priceNumber);
            return true;
        } catch (Exception e) {
            messages.add("ERROR! Not a number for price.");
            return false;
        }
    }

    private boolean setCompany(String companyName) {
        Criteria criteria = sessionController.getSession().createCriteria(Company.class);
        List<Company> companies =  criteria.list();
        for (Company c : companies) {
            if (c.getName().equalsIgnoreCase(companyName)) {
                fuelType.setCompany(c);
                return true;
            }
        }
        messages.add("ERROR!Company " + companyName + " doesn't exists.");
        return false;
    }
}

