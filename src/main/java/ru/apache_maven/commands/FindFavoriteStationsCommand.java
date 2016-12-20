package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;
import ru.apache_maven.models.Location;

import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by tania on 12/20/16.
 */
@Component
public class FindFavoriteStationsCommand implements Command {
    SessionController sessionController = SessionController.getInstance();
    boolean isFound = false;

    @Override
    public void execute() {

    }

    public void execute(String condition) {
        StringTokenizer st = new StringTokenizer(condition);
        String columnName = st.nextToken();

        if (columnName.equalsIgnoreCase("road")) {
            columnName = "roadNumber";
        }
        String value = st.nextToken();
        sessionController.getCurrentUser();
        Set<Company> favoriteCompanies = sessionController.getCurrentUser().getCompanies();
        for (Company company : favoriteCompanies) {
            List<GasStation> gasStations = company.getStations();
            for (GasStation gs : gasStations) {
                if (columnName.equalsIgnoreCase("roadNumber")) {
                    findAndPrintFoundStationsByRoad(value, company, gs);
                } else if (columnName.equalsIgnoreCase("city")) {
                    findAndPrintFoundStationsByCity(value, company, gs);
                } else if (columnName.equalsIgnoreCase("region")) {
                    findAndPrintFoundStationsByRegion(value, company, gs);
                }
            }
        }
        if(!isFound){
            System.out.println("Not found");
        }
    }

    private void findAndPrintFoundStationsByRoad(String value, Company company, GasStation gs) {
        String road = gs.getLocation().getRoadNumber();
        if (road != null && road.equalsIgnoreCase(value)) {
            System.out.println(company.getCompany_name() + " - "
                    + gs.getStationNumber() + " - "
                    + gs.getLocation().getAddress());
            isFound = true;
        }
    }

    private void findAndPrintFoundStationsByCity(String value, Company company, GasStation gs) {
        String city = gs.getLocation().getCity();
        if (city != null && city.equalsIgnoreCase(value)) {
            System.out.println(company.getCompany_name() + " - "
                    + gs.getStationNumber() + " - "
                    + gs.getLocation().getAddress());
            isFound = true;
        }
    }

    private void findAndPrintFoundStationsByRegion(String value, Company company, GasStation gs) {
        String region = gs.getLocation().getRegion();
        if (region != null && region.equalsIgnoreCase(value)) {
            System.out.println(company.getCompany_name() + " - "
                    + gs.getStationNumber() + " - "
                    + gs.getLocation().getAddress());
            isFound = true;
        }
    }
}
