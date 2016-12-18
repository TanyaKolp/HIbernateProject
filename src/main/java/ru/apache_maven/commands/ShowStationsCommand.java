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
        ArrayList<GasStation> stations = (ArrayList<GasStation>) sessionController.show(GasStation.class);
        for(GasStation gs : stations){
            System.out.println("Number   " + gs.getStationNumber());
            System.out.println("Company   " + gs.getCompany().getCompany_name());
            System.out.println("Address   " + gs.getLocation().getAddress());
        }
    }
}
