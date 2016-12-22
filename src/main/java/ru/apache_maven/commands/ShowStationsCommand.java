package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;

import java.util.ArrayList;

/**
 * Created by tania on 12/17/16.
 */
@Component
public class ShowStationsCommand implements Command{
    SessionController sessionController = SessionController.getInstance();
    @Override
    public void execute() {
        ArrayList<Company> companies = (ArrayList<Company>) sessionController.show(Company.class);
        for(Company company : companies){
            System.out.println(company.getCompany_name());
            for(GasStation gs : company.getStations())
                System.out.println("\t"+ gs.getStationNumber() + " - " + gs.getLocation().getAddress());
        }
    }

    @Override
    public void execute(String input) {

    }
}
