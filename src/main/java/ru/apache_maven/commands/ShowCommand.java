package ru.apache_maven.commands;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;
import ru.apache_maven.models.GasStation;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tania on 31.01.17.
 */
@Component
@Named("show")
public class ShowCommand implements Command {
    Logger logger = Logger.getLogger(ShowCommand.class);
    @Autowired
    SessionController sessionController ;
    private String help = "After word 'show' print:\n" +
            "1) show all or only favorite?\n2) what to show\n" +
            "1. all or my\n" +
            "2. company, priceList or station\n";


    @Override
    public Result execute(List<String> args) {
        Result result = new Result();
        ArrayList<String> messages = new ArrayList<>();
        try {
            sessionController.getSession().beginTransaction();
            logger.info("Transaction begin");
            if (args.size() == 2) {
                if (identifyEntity(args,messages)) {
                    result.setSuccess(true);
                    messages.add("Done.");
                } else {
                    result.setSuccess(false);
                    result.setHelp(getHelp());
                }
            } else {
                result.setSuccess(false);
                result.setHelp(getHelp());
                messages.add("ERROR! Not enough arguments.");
            }
            sessionController.getSession().getTransaction().commit();
            logger.info("Transaction commit.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setMessages(messages);
        return result;
    }

    @Override
    public String getHelp() {
        return this.help;
    }

    private boolean identifyEntity(List<String> args, ArrayList<String> messages) {
        String entity = args.get(1);
        Collection<Company> companies = getCompanies(args.get(0),messages);
        switch (entity) {
            case "company":
                if (showCompanies(companies,messages)) {
                    return true;
                }
                break;
            case "priceList":
                if (showPriceListsOfCompanies(companies,messages)) {
                    return true;
                }
                break;
            case "station":
                if (showStations(companies,messages)) {
                    return true;
                }
                break;
            default:
                messages.add("ERROR! No such entity");
        }
        return false;
    }


    private Collection<Company> getCompanies(String arg,  ArrayList<String> messages) {
        Collection<Company> companies = null;
        if (arg.equalsIgnoreCase("all")) {
            companies = (ArrayList<Company>) sessionController.getSession().createCriteria(Company.class).list();
        } else if (arg.equalsIgnoreCase("my")) {
            companies = sessionController.getCurrentUser().getCompanies();
        } else {
            messages.add("ERROR! Wrong argument. Choose 'all' or 'my'");
        }
        return companies;
    }

    private boolean showCompanies(Collection<Company> companies, ArrayList<String> messages) {
        if (companies == null) {
            return false;
        }
        for (Company c : companies) {
            messages.add(c.getId() + " -  " + c.getName());
        }
        return true;
    }


    private boolean showStations(Collection<Company> companies, ArrayList<String> messages) {
        if (companies == null) {
            return false;
        }
        for (Company company : companies) {
            messages.add(company.getName());
            for (GasStation gs : company.getStations())
                messages.add("\t" + gs.getName() + " - " + gs.getLocation().getAddress());
        }
        return true;
    }

    private boolean showPriceListsOfCompanies(Collection<Company> companies, ArrayList<String> messages) {
        if (companies == null) {
            return false;
        }
        for (Company company : companies) {
            messages.add(company.getName());
            for (FuelType fuelType : company.getFuelTypes()) {
                messages.add("    " + fuelType.getName() + " - " + fuelType.getPrice() + " rub");
            }
        }
        return true;
    }
}
