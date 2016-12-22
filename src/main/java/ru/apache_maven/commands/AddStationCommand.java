package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;
import ru.apache_maven.models.Location;

import java.util.StringTokenizer;

/**
 * Created by tania on 12/21/16.
 */
@Component
public class AddStationCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {

    }

    public void execute(String input) {
        GasStation newGS = new GasStation();
        StringTokenizer st = new StringTokenizer(input);
        Integer number;
        try {
            number = new Integer(st.nextToken());
            newGS.setStationNumber(number);

        } catch (Exception e) {
            System.out.println("not a number of station");
        }
        if (st.nextToken().equals("yes")) {
            newGS.setCafe(true);
        }
        if (st.nextToken().equals("yes")) {
            newGS.setShop(true);
        }
        Location location = new Location();
        location.setRegion(st.nextToken());
        location.setRoadNumber(st.nextToken());
        location.setCity(st.nextToken());
        location.setAddress(input.substring(input.indexOf("address")));
        newGS.setLocation(location);
        sessionController.saveSession(newGS);
    }
}
