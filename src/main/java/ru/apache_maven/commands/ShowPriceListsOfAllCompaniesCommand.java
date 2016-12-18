package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;

import java.util.ArrayList;

/**
 * Created by tania on 12/18/16.
 */
@Component
public class ShowPriceListsOfAllCompaniesCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {
        Criteria criteria = sessionController.getCriteria(Company.class);
        ArrayList<Company> list = (ArrayList<Company>) criteria.list();
        for (Company company : list) {
            System.out.println(company.getCompany_name());
            for (FuelType fuelType : company.getFuelTypes()) {
                System.out.println("    " + fuelType.getTitle() + " - " + fuelType.getPrice() + " rub");
            }
        }
    }
}
