package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by tania on 12/20/16.
 */
@Component
public class ShowFavoriteCompaniesCommand implements Command {
    SessionController sessionController = SessionController.getInstance();
    @Override
    public void execute() {
       Set<Company> favoriteCompanies = sessionController.getCurrentUser().getCompanies();
        for(Company c : favoriteCompanies){
            System.out.println(c.getCompany_id() + " -  " + c.getCompany_name());
        }
    }

    @Override
    public void execute(String input) {

    }
}
