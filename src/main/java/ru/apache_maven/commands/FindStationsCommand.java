package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;
import ru.apache_maven.models.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by tania on 12/18/16.
 */
@Component
public class FindStationsCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {
    }

    public void execute(String condition) {
        StringTokenizer st = new StringTokenizer(condition);
        String columnName = st.nextToken();
        String value = st.nextToken();
        Criteria criteria = sessionController.getCriteria(Location.class);
        List<Location> locations = (List<Location>) criteria.add(Restrictions.eq(columnName, value)).list();
        for (Location location : locations) {
            System.out.println(location.getGasStations().getStationNumber() + " - " +
                    location.getGasStations().getCompany().getCompany_name()+ " - " +
            location.getAddress());
        }
    }
}
