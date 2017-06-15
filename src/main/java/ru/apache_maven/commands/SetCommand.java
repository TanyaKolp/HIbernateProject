package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.User;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tania on 12/17/16.
 */
@Component
@Named("set")
public class SetCommand implements Command {
    private String help = "\nAfter word 'set' print:\n" +
            "1) what attribute\n2) value\n" +
            "\t1. favorite (means favorite companies) or admin\n" +
            "\t2. for favorite:\n\t\tcompany name\n" +
            "\tfor admin:\n\t\tuser login or ID.\n";
    @Autowired
    SessionController sessionController;
    private ArrayList<String> messages;

    @Override
    public Result execute(List<String> input) {
        Result result = new Result();
        messages = new ArrayList<String>();
        sessionController.getSession().beginTransaction();
        if (input.size() > 1) {
            if (set(input)) {
                result.setSuccess(true);
                messages.add("\nDone.");
            } else {
                result.setSuccess(false);
                result.setHelp(getHelp());
            }
        } else {
            messages.add("ERROR! Not enough arguments.");
            result.setSuccess(false);
            result.setHelp(getHelp());
        }
        sessionController.getSession().getTransaction().commit();
        result.setMessages(messages);
        return result;
    }

    @Override
    public String getHelp() {
        return this.help;
    }

    private boolean set(List<String> args) {
        String attribute = args.get(0);

        if (attribute.equalsIgnoreCase("favorite")) {
            if (setFavoriteCompany(args.get(1))) {
                return true;
            }
        } else if (attribute.equalsIgnoreCase("admin")) {
            if (setAdmin(args.get(1))) {
                return true;
            }
        } else {
            messages.add("ERROR! Wrong attribute. Choose 'favorite' or 'admin'");
        }
        return false;
    }

    private boolean setFavoriteCompany(String companyName) {
        User user = sessionController.getCurrentUser();
        Criteria criteria = sessionController.getSession().createCriteria(Company.class);
        Company company = (Company) criteria.add(Restrictions.eq("name", companyName)).uniqueResult();
        if (company != null) {
            user.getCompanies().add(company);
            sessionController.getSession().saveOrUpdate(user);
        } else {
            messages.add("Company '" + companyName + "' doesn't exists");
        }
        return false;
    }

    private boolean setAdmin(String identifier) {
        if (!sessionController.getCurrentUser().isAdmin()) {
            messages.add("You have no rights!");
            return false;
        }
        User user;
        int iD;
        try {
            iD = new Integer(identifier);
            user = sessionController.getSession().get(User.class, iD);
        } catch (NumberFormatException e) {
            Criteria criteria = sessionController.getSession().createCriteria(Company.class);
            user = (User) criteria.add(Restrictions.eq("login", identifier)).uniqueResult();
        }
        if (user != null) {
            user.setAdmin(true);
            sessionController.getSession().saveOrUpdate(user);
            return true;
        }
        messages.add("There is no user with identifier '" + identifier + "'");
        return false;
    }
}
