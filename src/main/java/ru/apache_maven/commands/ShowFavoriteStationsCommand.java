package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by tania on 12/20/16.
 */
@Component
public class ShowFavoriteStationsCommand implements Command {
    SessionController sessionController = SessionController.getInstance();
    @Override
    public void execute() {
        Set<Company> favoriteCompanies = sessionController.getCurrentUser().getCompanies();
        for(Company company : favoriteCompanies){
            System.out.println(company.getCompany_name());
            for(GasStation gs : company.getStations())
            System.out.println("\t"+ gs.getStationNumber() + " - " + gs.getLocation().getAddress());
        }
    }
}
