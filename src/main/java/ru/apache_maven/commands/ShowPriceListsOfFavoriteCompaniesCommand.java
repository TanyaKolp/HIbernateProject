package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by tania on 12/18/16.
 */
@Component
public class ShowPriceListsOfFavoriteCompaniesCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {
        Set<Company> companies = (Set<Company>) sessionController.getCurrentUser().getCompanies();
        for (Company company : companies) {
            System.out.println(company.getCompany_name());
            for (FuelType fuelType : company.getFuelTypes()) {
                System.out.println("    " + fuelType.getTitle() + " - " + fuelType.getPrice() + " rub");
            }
        }
    }

}
