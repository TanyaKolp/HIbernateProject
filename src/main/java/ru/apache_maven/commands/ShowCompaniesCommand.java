package ru.apache_maven.commands;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;

import java.util.ArrayList;

/**
 * Created by tania on 12/13/16.
 */
@Component
public class ShowCompaniesCommand implements Command {
    SessionController sessionController = SessionController.getInstance();
    @Override
    public void execute() {
        ArrayList<Company> companies = (ArrayList<Company>) sessionController.show(Company.class);
        for(Company c : companies){
            System.out.println(c.getCompany_id() + " -  " + c.getCompany_name());
        }
    }
}
