package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.User;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by tania on 12/17/16.
 */
@Component
public class SetFavoriteCompaniesCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {
        System.out.println("setting F company");
    }

    public void execute(String value) {
        User user = sessionController.getCurrentUser();
        StringTokenizer st = new StringTokenizer(value, ", ");
        ArrayList<String> companyNames = new ArrayList<>();
        while (st.hasMoreTokens()) {
            companyNames.add(st.nextToken());
        }
        for (String companyName : companyNames) {
            Criteria criteria = sessionController.getCriteria(Company.class);
            Company company = (Company) criteria.add(Restrictions.eq("company_name", companyName)).uniqueResult();
            if (company != null) {
                user.getCompanies().add(company);
            }else {
                System.out.println("Company '"+companyName+"' doesn't exists");
            }
        }
        sessionController.saveSession(user);
        System.out.println("Done.");
    }
}
