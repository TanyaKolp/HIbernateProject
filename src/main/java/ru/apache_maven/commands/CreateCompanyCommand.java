package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tania on 12/21/16.
 */
@Component
@Named("company")
public class CreateCompanyCommand implements Creation {
    @Autowired
    SessionController sessionController;

    @Override
    public Result execute(List<String> arg) {
        Result result = new Result();
        ArrayList<String> messages = new ArrayList<>();
        try {
            sessionController.getSession().beginTransaction();
            String companyName = arg.get(0);
            Criteria criteria = sessionController.getSession().createCriteria(Company.class);
            Company company = (Company) criteria.add(Restrictions.eq("name", companyName)).uniqueResult();
            if (company == null) {
                Company newCompany = new Company(companyName);
                sessionController.getSession().saveOrUpdate(newCompany);
                sessionController.getSession().getTransaction().commit();
                result.setSuccess(true);
                messages.add("Done.");
            } else {
                result.setSuccess(false);
                messages.add("Company '" + companyName + "' already exists");
            }
        } catch (HibernateException e) {
            sessionController.getSession().getTransaction().rollback();
            result.setSuccess(false);
            messages.add("sm is wrong");
            e.printStackTrace();
        }
        result.setMessages(messages);
        return result;
    }
}
